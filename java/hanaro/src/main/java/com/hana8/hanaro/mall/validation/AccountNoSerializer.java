package com.hana8.hanaro.mall.validation;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class AccountNoSerializer extends JsonSerializer<String> {

  @Override
  public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
    System.out.println("AccountNoSerializer called with: " + value);
    if (value != null) {
      // 하이픈을 제거한 순수 숫자 11자리 추출
      String clean = value.replaceAll("-", "");

      if (clean.length() == 11) {
        // ###-####-#### 형식으로 포맷팅
        String formatted = String.format("%s-%s-%s",
            clean.substring(0, 3),
            clean.substring(3, 7),
            clean.substring(7, 11));
        gen.writeString(formatted);
      } else {
        gen.writeString(value); // 11자리가 아니면 원본 그대로 출력 (예외 상황 대비)
      }
    } else {
      gen.writeNull();
    }
  }
}
