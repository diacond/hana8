package com.hana8.demo.common.validator;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CardNoValidatorTest {

    @Test
    void testCardNoValidator() {
        CardNoValidator validator = new CardNoValidator();
        assertTrue(validator.isValid("1234-1234-5678-5678", null));
        assertTrue(validator.isValid("1234 1234 5678 5678", null));
        assertTrue(validator.isValid("1234123456785678", null));
        assertFalse(validator.isValid("1234-1234-5678-567", null)); // 15 digits
        assertFalse(validator.isValid("1234-1234-5678-56789", null)); // 17 digits
        assertFalse(validator.isValid("1234-1234-5678-567a", null)); // Non-digit
    }
}
