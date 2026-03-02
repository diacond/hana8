package com.hana8.demo.common.serializer;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CardNoSerializerTest {

    @Test
    void testCardNoSerializerFormat() {
        CardNoSerializer serializer = new CardNoSerializer();
        assertEquals("1234-56**-****-5678", serializer.format("1234567812345678"));
        assertEquals("1111-22**-****-4444", serializer.format("1111222233334444"));
        assertEquals("123456781234567", serializer.format("123456781234567")); // 15 digits (no change)
    }
}
