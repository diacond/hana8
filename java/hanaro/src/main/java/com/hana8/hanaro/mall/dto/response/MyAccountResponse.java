package com.hana8.hanaro.mall.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MyAccountResponse {
  private String accountNumber;    // 계좌번호
  private Long balance;            // 현재 잔액
  private String productName;      // 상품명
  private boolean isFreeAccount;

  private LocalDateTime createdAt;
  private String status;
  private BigDecimal currentInterest;

  // JSON 출력 시 자동으로 이 메서드를 사용하도록 설정 (Getter 오버라이딩)
  public String getAccountNumber() {
    if (accountNumber == null) return null;
    String clean = accountNumber.replaceAll("-", "");
    if (clean.length() == 11) {
      return String.format("%s-%s-%s",
          clean.substring(0, 3),
          clean.substring(3, 7),
          clean.substring(7, 11));
    }
    return accountNumber;
  }
}
