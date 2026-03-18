package com.hana8.hanaro.mall.util;

import com.hana8.hanaro.mall.entity.History;
import com.hana8.hanaro.mall.entity.Product;
import com.hana8.hanaro.mall.entity.Transaction;
import com.hana8.hanaro.mall.enums.Status;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class InterestCalculator {

  /**
   * 트랜잭션 기반 실시간 이자 계산
   * @param history 가입 내역
   * @param transactions 해당 계좌로 입금된 내역들
   */
  public static BigDecimal calculate(History history, List<Transaction> transactions) {
    if (transactions == null || transactions.isEmpty()) {
      return BigDecimal.ZERO;
    }

    Product product = history.getProduct();
    
    // 1. 계산 종료일 결정 (만기일 vs 현재일 중 빠른 날)
    LocalDateTime maturityDate = history.getCreatedAt().plusMonths(product.getDurationMonths());
    LocalDateTime endDate = LocalDateTime.now().isAfter(maturityDate) ? maturityDate : LocalDateTime.now();

    // 2. 이율 결정 (상태에 따라 만기/중도해지 이율 적용)
    BigDecimal yieldRate = (history.getStatus() == Status.QUIT)
        ? product.getCancellationYield()
        : product.getMaturityYield();
    
    BigDecimal annualRate = yieldRate.divide(new BigDecimal("100"), 10, RoundingMode.HALF_UP);
    BigDecimal totalInterest = BigDecimal.ZERO;

    // 3. 각 입금 트랜잭션별 이자 합산
    for (Transaction tx : transactions) {
      // 입금일 ~ 종료일까지의 일수 계산
      long days = ChronoUnit.DAYS.between(tx.getCreatedAt(), endDate);
      if (days <= 0) continue;

      // 개별 이자 = 입금액 * 이율 * (경과일수/365)
      BigDecimal interest = new BigDecimal(tx.getAmount())
          .multiply(annualRate)
          .multiply(new BigDecimal(days).divide(new BigDecimal("365"), 10, RoundingMode.HALF_UP));
          
      totalInterest = totalInterest.add(interest);
    }

    return totalInterest.setScale(0, RoundingMode.FLOOR);
  }
}
