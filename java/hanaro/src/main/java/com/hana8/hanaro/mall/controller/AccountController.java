package com.hana8.hanaro.mall.controller;

import com.hana8.hanaro.mall.dto.response.MyAccountResponse;
import com.hana8.hanaro.mall.dto.response.MyDashboardResponse;
import com.hana8.hanaro.mall.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/accounts")
@RequiredArgsConstructor
public class AccountController {

  private final AccountService accountService;

  /**
   * [User] 내 가입 내역(전체 계좌) 보기
   * GET /api/accounts/my?userId=1
   */
  // AccountController.java
  @GetMapping("/my")
  public ResponseEntity<MyDashboardResponse> getMyAccountDashboard(@RequestParam Long userId) {
    return ResponseEntity.ok(accountService.getMyAssetDashboard(userId));
  }
}
