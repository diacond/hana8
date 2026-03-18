package com.hana8.hanaro.mall.dto.response;

import java.math.BigDecimal;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyDashboardResponse {
  private Long totalBalance;        // 모든 계좌 원금 합계
  private BigDecimal totalInterest; // 모든 예적금의 실시간 이자 합계
  private List<MyAccountResponse> accounts; // 개별 계좌 상세 리스트
}
