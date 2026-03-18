package com.hana8.hanaro.mall.repository;

import com.hana8.hanaro.mall.entity.Transaction;
import com.hana8.hanaro.mall.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

  // 특정 계좌로 들어온 입금 내역 조회 (이자 계산용)
  @Query("SELECT t FROM Transaction t WHERE t.toAccount.id = :accountId AND (t.type = 'TRANSFER' OR t.type = 'DEPOSIT')")
  List<Transaction> findInboundTransactions(@Param("accountId") Long accountId);
}
