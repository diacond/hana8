package com.hana8.demo.common.serializer;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class TelnoSerializer extends JsonSerializer<String> {

	@Override
	public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
		if (value == null) {
			gen.writeNull();
			return;
		}

		gen.writeString(format(value.replaceAll("[\\s-]", "")));
	}

	private String format(String tel) {
		return switch (tel.length()) {
			case 8 -> tel.replaceAll("(\\d{4})(\\d{4})", "$1-$2");                   // 1588-1234
			case 9 -> tel.replaceAll("(\\d{2})(\\d{3})(\\d{4})", "$1-$2-$3");        // 02-123-4567
			case 10 -> tel.replaceAll("(\\d{2,3})(\\d{3,4})(\\d{4})", "$1-$2-$3");   // 010-123-4567
			case 11 -> tel.replaceAll("(\\d{3,4})(\\d{4})(\\d{4})", "$1-$2-$3");     // 010-1234-5678
			case 12 -> tel.replaceAll("(\\d{4})(\\d{4})(\\d{4})", "$1-$2-$3");       // 0502-1234-5678
			default -> tel;
		};
	}

}
