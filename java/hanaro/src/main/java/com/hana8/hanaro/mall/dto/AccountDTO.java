package com.hana8.hanaro.mall.dto;

import com.hana8.hanaro.mall.validation.AccountNo;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AccountDTO {

  private Long id;

  @AccountNo // (우리가 만든 것) 사용자가 입력할 때 숫자 11자리인지 검증!
  private String accountNumber;

  private Long balance;
  private Long userId; // 어느 회원의 계좌인지 연결

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
