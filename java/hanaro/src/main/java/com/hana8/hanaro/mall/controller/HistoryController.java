package com.hana8.hanaro.mall.controller;

import com.hana8.hanaro.mall.dto.AccountDTO;
import com.hana8.hanaro.mall.dto.HistoryDTO;
import com.hana8.hanaro.mall.service.HistoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/history")
@RequiredArgsConstructor
public class HistoryController {

  private final HistoryService historyService;

  /**
   * 상품 가입 API
   * POST /api/history/subscribe/{productId}?userId=1
   * (현재 로그인이 없으므로 userId를 파라미터로 임시로 받습니다)
   */
  @PostMapping("/subscribe/{productId}")
  public ResponseEntity<HistoryDTO> subscribeProduct(
      @PathVariable Long productId,
      @RequestParam Long userId,
      @Valid @RequestBody AccountDTO accountDTO) { // 우리가 만든 @AccountNo 유효성 검사가 여기서 작동합니다!

    HistoryDTO result = historyService.subscribeProduct(userId, productId, accountDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(result);
  }

  /**
   * 상품 중도 해지 API
   * PATCH /api/history/{historyId}/quit?userId=1
   */
  @PatchMapping("/{historyId}/quit")
  public ResponseEntity<String> quitProduct(
      @PathVariable Long historyId,
      @RequestParam Long userId) {

    historyService.quitSubscription(historyId, userId);
    return ResponseEntity.ok("성공적으로 해지되었습니다. (가입내역 ID: " + historyId + ")");
  }
}
