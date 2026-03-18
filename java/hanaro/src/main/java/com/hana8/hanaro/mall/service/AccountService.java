package com.hana8.hanaro.mall.service;

import com.hana8.hanaro.mall.dto.response.MyAccountResponse;
import com.hana8.hanaro.mall.dto.response.MyDashboardResponse;
import com.hana8.hanaro.mall.entity.Account;
import com.hana8.hanaro.mall.entity.History;
import com.hana8.hanaro.mall.entity.Product;
import com.hana8.hanaro.mall.enums.Status;
import com.hana8.hanaro.mall.repository.AccountRepository;
import com.hana8.hanaro.mall.repository.HistoryRepository;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

  private final AccountRepository accountRepository;
  private final HistoryRepository historyRepository;
  private final com.hana8.hanaro.mall.repository.TransactionRepository transactionRepository;

  /**
   * [User] 내 전체 계좌 + 총 자산 + 총 이자 대시보드 보기
   */
  public MyDashboardResponse getMyAssetDashboard(Long userId) {
    List<Account> allAccounts = accountRepository.findAllByUserId(userId);
    List<History> allHistories = historyRepository.findAllByUserId(userId);

    List<MyAccountResponse> responseList = new ArrayList<>();
    Long totalBalance = 0L;
    BigDecimal totalInterest = BigDecimal.ZERO;

    for (Account account : allAccounts) {
      totalBalance += account.getBalance();

      History matchedHistory = allHistories.stream()
          .filter(h -> h.getAccount().getId().equals(account.getId()))
          .findFirst()
          .orElse(null);

      if (matchedHistory == null) {
        // 자유입출금
        responseList.add(MyAccountResponse.builder()
            .accountNumber(account.getAccountNumber())
            .balance(account.getBalance())
            .productName("하나 자유입출금 통장")
            .isFreeAccount(true)
            .build());
      } else {
        // 예적금: ACTIVE 상태일 때만 실시간 이자 계산 엔진 가동!
        BigDecimal currentInterest = BigDecimal.ZERO;
        
        if (matchedHistory.getStatus() == Status.ACTIVE) {
          List<com.hana8.hanaro.mall.entity.Transaction> txs = transactionRepository.findInboundTransactions(account.getId());
          currentInterest = com.hana8.hanaro.mall.util.InterestCalculator.calculate(matchedHistory, txs);
        }
        
        totalInterest = totalInterest.add(currentInterest);

        responseList.add(MyAccountResponse.builder()
            .accountNumber(account.getAccountNumber())
            .balance(account.getBalance())
            .productName(matchedHistory.getProduct().getName())
            .isFreeAccount(false)
            .createdAt(matchedHistory.getCreatedAt())
            .status(matchedHistory.getStatus().name())
            .currentInterest(currentInterest)
            .build());
      }
    }

    return MyDashboardResponse.builder()
        .totalBalance(totalBalance)
        .totalInterest(totalInterest)
        .accounts(responseList)
        .build();
  }
}
