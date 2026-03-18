package com.hana8.hanaro.mall.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AccountNoValidator implements ConstraintValidator<AccountNo, String> {

  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    if (value == null || value.isBlank()) {
      return false;
    }

    // 사용자가 입력한 값에서 하이픈(-)을 모두 제거하고 숫자만 남김
    String numericOnly = value.replaceAll("-", "");

    // 숫자 11자리인지 검증 (정규식: ^\d{11}$)
    return numericOnly.matches("^\\d{11}$");
  }
}
