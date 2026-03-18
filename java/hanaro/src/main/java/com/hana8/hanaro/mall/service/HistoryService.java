package com.hana8.hanaro.mall.service;

import com.hana8.hanaro.mall.dto.AccountDTO;
import com.hana8.hanaro.mall.dto.HistoryDTO;
import com.hana8.hanaro.mall.entity.Account;
import com.hana8.hanaro.mall.entity.History;
import com.hana8.hanaro.mall.entity.Product;
import com.hana8.hanaro.mall.entity.Transaction;
import com.hana8.hanaro.mall.entity.User;
import com.hana8.hanaro.mall.enums.OnSale;
import com.hana8.hanaro.mall.enums.Status;
import com.hana8.hanaro.mall.repository.AccountRepository;
import com.hana8.hanaro.mall.repository.HistoryRepository;
import com.hana8.hanaro.mall.repository.ProductRepository;
import com.hana8.hanaro.mall.repository.UserRepository;
import com.hana8.hanaro.mall.mapper.HistoryMapper;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.temporal.ChronoUnit;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HistoryService {

  private final HistoryRepository historyRepository;
  private final AccountRepository accountRepository;
  private final UserRepository userRepository;
  private final ProductRepository productRepository;
  private final TransactionService transactionService;
  private final com.hana8.hanaro.mall.repository.TransactionRepository transactionRepository;
  private final HistoryMapper historyMapper;

  /**
   * [User] 상품 가입 로직 (이체 기능 포함)
   */
  @Transactional
  public HistoryDTO subscribeProduct(Long userId, Long productId, AccountDTO accountDTO) {

    User user = userRepository.findById(userId)
        .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

    if (product.getOnsale() == OnSale.NO) {
      throw new IllegalStateException("현재 판매가 중단된 상품입니다.");
    }

    // 1. 자유입출금 계좌 확인 (여기서 출금)
    Account freeAccount = accountRepository.findFreeAccountByUserId(userId);
    if (freeAccount.getBalance() < product.getDepositAmount()) {
      throw new IllegalArgumentException(
          "자유입출금 계좌의 잔액이 부족합니다. (필요: " + product.getDepositAmount() + "원)");
    }

    // 2. 새로운 상품 계좌 생성
    String newAccountNumber = accountDTO.getAccountNumber();
    if (accountRepository.existsByAccountNumber(newAccountNumber)) {
      throw new IllegalArgumentException("이미 사용 중인 계좌번호입니다.");
    }

    Account newAccount = Account.builder()
        .user(user)
        .accountNumber(newAccountNumber)
        .balance(0L)
        .build();
    Account savedAccount = accountRepository.save(newAccount);

    // 3. 실질적 이체 실행 (자유계좌 -> 상품계좌)
    transactionService.transfer(freeAccount, savedAccount, product.getDepositAmount(),
        product.getName() + " 가입 납입");

    // 4. 가입 내역 기록
    History history = History.builder()
        .user(user)
        .product(product)
        .account(savedAccount)
        .createdAt(LocalDateTime.now())
        .status(Status.ACTIVE)
        .build();
    History savedHistory = historyRepository.save(history);

    return historyMapper.toDTO(savedHistory);
  }

  /**
   * [Admin] 닉네임으로 회원의 가입 내역 조회
   */
  public List<HistoryDTO> getHistoriesByNickname(String nickname) {
    List<History> histories = historyRepository.findAllByUser_Nickname(nickname);
    return histories.stream()
        .map(historyMapper::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * [Admin] 수동 만기 처리 로직 (트랜잭션 기반)
   */
  @Transactional
  public void completeMaturity(Long historyId) {
    History history = historyRepository.findById(historyId)
        .orElseThrow(() -> new IllegalArgumentException("해당 가입 내역을 찾을 수 없습니다."));

    if (history.getStatus() != Status.ACTIVE) {
      throw new IllegalStateException("현재 활동 중인(ACTIVE) 내역만 만기 처리가 가능합니다.");
    }

    // 1. 이자 계산 (트랜잭션 기반)
    List<com.hana8.hanaro.mall.entity.Transaction> txs = transactionRepository.findInboundTransactions(
        history.getAccount().getId());
    BigDecimal finalInterest = com.hana8.hanaro.mall.util.InterestCalculator.calculate(history,
        txs);

    // 2. 상환 처리 (원금 + 이자 -> 자유계좌)
    Account productAccount = history.getAccount();
    Account freeAccount = accountRepository.findFreeAccountByUserId(history.getUser().getId());

    transactionService.recordMaturity(productAccount, freeAccount, productAccount.getBalance(),
        finalInterest.longValue(), false);

    // 3. 상태 업데이트
    history.updateStatus(Status.MATURED);
  }

  /**
   * [User] 중도 해지 로직 (트랜잭션 기반)
   */
  @Transactional
  public void quitSubscription(Long historyId, Long userId) {
    History history = historyRepository.findById(historyId)
        .orElseThrow(() -> new IllegalArgumentException("해당 가입 내역을 찾을 수 없습니다."));

    if (!history.getUser().getId().equals(userId)) {
      throw new IllegalArgumentException("본인의 가입 내역만 해지할 수 있습니다.");
    }

    if (history.getStatus() != Status.ACTIVE) {
      throw new IllegalStateException("현재 활동 중인(ACTIVE) 내역만 해지가 가능합니다.");
    }

    // 1. 상태 업데이트 (QUIT) - 이자 계산 시 상태를 보고 이율을 결정하므로 먼저 변경하거나 파라미터로 전달
    history.updateStatus(Status.QUIT);

    // 2. 이자 계산 (트랜잭션 기반, QUIT 상태이므로 cancellationYield 적용됨)
    List<Transaction> txs = transactionRepository.findInboundTransactions(
        history.getAccount().getId());
    BigDecimal cancellationInterest = com.hana8.hanaro.mall.util.InterestCalculator.calculate(
        history, txs);

    // 3. 상환 처리
    Account productAccount = history.getAccount();
    Account freeAccount = accountRepository.findFreeAccountByUserId(userId);

    transactionService.recordMaturity(productAccount, freeAccount, productAccount.getBalance(),
        cancellationInterest.longValue(), true);
  }
}
