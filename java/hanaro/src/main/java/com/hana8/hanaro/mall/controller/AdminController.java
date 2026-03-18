package com.hana8.hanaro.mall.controller;

import com.hana8.hanaro.mall.dto.HistoryDTO;
import com.hana8.hanaro.mall.dto.UserDTO;
import com.hana8.hanaro.mall.dto.response.MyDashboardResponse;
import com.hana8.hanaro.mall.service.AccountService;
import com.hana8.hanaro.mall.service.HistoryService;
import com.hana8.hanaro.mall.service.UserService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

  private final UserService userService;
  private final AccountService accountService;
  private final HistoryService historyService;

  /**
   * 1. 전체 회원 목록 조회
   */
  @GetMapping("/users")
  public ResponseEntity<List<UserDTO>> getAllUsers() {
    return ResponseEntity.ok(userService.getAllUsers());
  }

  /**
   * 2. 회원별 가입 상품 내역 조회
   * userId를 경로 변수로 받아 해당 유저의 모든 계좌와 연결된 상품 정보를 반환합니다.
   */
  @GetMapping("/users/{userId}/histories")
  public ResponseEntity<MyDashboardResponse> getUserHistories(@PathVariable Long userId) {
    MyDashboardResponse dashboard = accountService.getMyAssetDashboard(userId);
    return ResponseEntity.ok(dashboard);
  }

  /**
   * 3. 닉네임으로 회원의 가입 내역 조회
   * GET /api/admin/users/search?nickname=hong
   */
  @GetMapping("/users/search")
  public ResponseEntity<List<HistoryDTO>> searchUserHistories(@RequestParam String nickname) {
    return ResponseEntity.ok(historyService.getHistoriesByNickname(nickname));
  }

  /**
   * [Admin] 특정 가입 내역 강제 만기 처리 API
   * PATCH /api/admin/histories/{historyId}/mature
   */
  @PatchMapping("/histories/{historyId}/mature")
  public ResponseEntity<String> forceMaturity(@PathVariable Long historyId) {
    historyService.completeMaturity(historyId);
    return ResponseEntity.ok("성공적으로 만기 처리되었습니다. (ID: " + historyId + ")");
  }

}
