package com.hana8.hanaro.mall.controller;

import com.hana8.hanaro.mall.security.JwtUtil;
import com.hana8.hanaro.mall.security.exception.CustomJwtException;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "인증", description = "토큰 갱신 API")
public class JwtRefreshController {

    private final JwtUtil jwtUtil;

    @RequestMapping("/api/auth/refresh")
    @Tag(name = "인증", description = "토큰 갱신")
    public Map<String, Object> refresh(@RequestHeader("Authorization") String authHeader, String refreshToken) {
        if (refreshToken == null)
            throw new CustomJwtException("NULL_REFRESHTOKEN");

        if (authHeader == null || authHeader.length() < 7)
            throw new CustomJwtException("INVALID_TOKEN_STRING");

        String accessToken = authHeader.substring(7);

        // Access Token이 만료되지 않았으면 그대로 반환
        if (!didExpireToken(accessToken)) {
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        // Refresh Token 검증
        Map<String, Object> claims = jwtUtil.validateToken(refreshToken);

        // 새 Access Token 생성 (10분)
        String newAccessToken = jwtUtil.generateToken(claims, 10);

        // Refresh Token의 만료 시간이 1시간 미만으로 남았으면 새 Refresh Token 생성 (24시간)
        String newRefreshToken = isSomeLeftTime((Long) claims.get("exp"))
                ? refreshToken
                : jwtUtil.generateToken(claims, 60 * 24);

        return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
    }

    private boolean isSomeLeftTime(Long exp) {
        long nowSec = System.currentTimeMillis() / 1000;
        return (exp - nowSec) > 60 * 60; // 1시간 이상 남았는지 확인
    }

    private boolean didExpireToken(String accessToken) {
        try {
            jwtUtil.validateToken(accessToken);
        } catch (CustomJwtException e) {
            if (e.getMessage().equals("Expired")) {
                return true;
            }
        }
        return false;
    }
}
