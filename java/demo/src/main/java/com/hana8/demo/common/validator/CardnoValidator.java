package com.hana8.demo.common.validator;

import java.util.regex.Pattern;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CardnoValidator implements ConstraintValidator<Cardno, String> {
	private static final Pattern pattern = Pattern.compile("^\\d{16}$");

	@Override
	public boolean isValid(String value, ConstraintValidatorContext ctx) {
		if (value == null || value.isBlank())
			return true;

		String replaceSpaceAndHyphen = value.replaceAll("[\\s-]", "");
		return pattern.matcher(replaceSpaceAndHyphen).matches();
	}
}
