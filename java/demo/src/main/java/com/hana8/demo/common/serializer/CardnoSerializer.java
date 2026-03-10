package com.hana8.demo.common.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CardnoSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (value == null) {
			gen.writeNull();
			return;
		}

		gen.writeString(format(value.replaceAll("[\\s-]", "")));
	}

	private String format(String cardno) {
		if (cardno.length() == 16) {
			return cardno.replaceAll("(\\d{4})(\\d{4})(\\d{4})(\\d{4})", "$1-$2-$3-$4");
		}
		return cardno;
	}
}
