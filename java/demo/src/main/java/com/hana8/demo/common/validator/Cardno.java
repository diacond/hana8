package com.hana8.demo.common.validator;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CardnoValidator.class)
public @interface Cardno {
	String message() default "유효하지 않은 카드번호 형식입니다.";

	Class<?>[] groups() default {};

	Class<? extends Payload>[] payload() default {};
}
