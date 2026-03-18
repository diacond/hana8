package com.hana8.hanaro.mall.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

  // 1. 비즈니스 로직 예외 (Service에서 던진 IllegalArgumentException 처리)
  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException e) {
    String message = e.getMessage();
    // 교재 스타일대로 "Warn: " 접두사를 붙여 문자열로 반환합니다.
    return ResponseEntity.badRequest().body("Warn: " + message);
  }

  // 2. @Valid 또는 @Validated 오류 처리 (과제 명세서 필수 조건!)
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<String> handleValidationExceptions(MethodArgumentNotValidException e) {
    StringBuilder errorMessage = new StringBuilder("Validation Error: ");

    // 어떤 필드(이메일, 계좌번호 등)에서 에러가 났는지 모두 모아서 하나의 문자열로 만듭니다.
    for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
      errorMessage.append("[")
          .append(fieldError.getField())
          .append("] ")
          .append(fieldError.getDefaultMessage())
          .append(" / ");
    }

    return ResponseEntity.badRequest().body(errorMessage.toString());
  }

  // 3. 나머지 모든 예상치 못한 예외 처리 (교재 내용 반영)
  @ExceptionHandler(Exception.class)
  public ResponseEntity<String> handleAll(Exception e) {
    return ResponseEntity.internalServerError().body("Server Error: " + e.getMessage());
  }
}
