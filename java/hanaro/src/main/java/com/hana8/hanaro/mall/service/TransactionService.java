package com.hana8.hanaro.mall.service;

import com.hana8.hanaro.mall.entity.Account;
import com.hana8.hanaro.mall.entity.Transaction;
import com.hana8.hanaro.mall.enums.TransactionType;
import com.hana8.hanaro.mall.repository.AccountRepository;
import com.hana8.hanaro.mall.repository.TransactionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionService {

  private final AccountRepository accountRepository;
  private final TransactionRepository transactionRepository;

  /**
   * 계좌 간 이체 (출금계좌 -> 입금계좌)
   */
  public void transfer(Account from, Account to, Long amount, String description) {
    if (from.getBalance() < amount) {
      throw new IllegalArgumentException("잔액이 부족합니다.");
    }

    from.updateBalance(from.getBalance() - amount);
    to.updateBalance(to.getBalance() + amount);

    Transaction transaction = Transaction.builder()
        .fromAccount(from)
        .toAccount(to)
        .amount(amount)
        .type(TransactionType.TRANSFER)
        .description(description)
        .createdAt(LocalDateTime.now())
        .build();

    transactionRepository.save(transaction);
  }

  /**
   * 만기/해지 시 원금 + 이자 반환 기록
   */
  public void recordMaturity(Account productAccount, Account freeAccount, Long principal, Long interest, boolean isCancelled) {
    // 1. 원금 반환 (적금계좌 -> 자유계좌)
    productAccount.updateBalance(productAccount.getBalance() - principal);
    freeAccount.updateBalance(freeAccount.getBalance() + principal);

    Transaction principalTx = Transaction.builder()
        .fromAccount(productAccount)
        .toAccount(freeAccount)
        .amount(principal)
        .type(isCancelled ? TransactionType.CANCEL : TransactionType.MATURITY)
        .description(isCancelled ? "중도 해지 원금 상환" : "만기 원금 상환")
        .build();
    transactionRepository.save(principalTx);

    // 2. 이자 지급 (외부 -> 자유계좌)
    freeAccount.updateBalance(freeAccount.getBalance() + interest);

    Transaction interestTx = Transaction.builder()
        .fromAccount(null) // 은행 시스템에서 지급
        .toAccount(freeAccount)
        .amount(interest)
        .type(TransactionType.INTEREST)
        .description(isCancelled ? "중도 해지 이자 지급" : "만기 이자 지급")
        .build();
    transactionRepository.save(interestTx);
  }
}
