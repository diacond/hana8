package com.hana8.hanaro.mall.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.hana8.hanaro.mall.validation.AccountNo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDTO {

  private Long id; // 응답용

  @NotBlank(message = "이메일은 필수 입력값입니다.")
  @Email(message = "이메일 형식이 올바르지 않습니다.")
  private String email;

  @NotBlank(message = "비밀번호는 필수 입력값입니다.")
  @Size(min = 8, message = "비밀번호는 8자 이상이어야 합니다.")
  @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) // 핵심! 응답 JSON에서는 숨겨줍니다.
  private String password;

  @NotBlank(message = "닉네임은 필수 입력값입니다.")
  private String nickname;

  @AccountNo // 우리가 만든 11자리 검증 어노테이션
  private String accountNumber;

  private String role; // 응답용

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
