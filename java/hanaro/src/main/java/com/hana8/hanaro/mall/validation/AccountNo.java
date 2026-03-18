package com.hana8.hanaro.mall.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AccountNoValidator.class) // 이 어노테이션이 붙으면 어떤 검증기를 실행할지 지정
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AccountNo {
  String message() default "계좌번호는 숫자 11자리여야 합니다.";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}
