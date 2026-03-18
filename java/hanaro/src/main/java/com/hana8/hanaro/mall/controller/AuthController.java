package com.hana8.hanaro.mall.controller;

import com.hana8.hanaro.mall.dto.LoginRequest;
import com.hana8.hanaro.mall.dto.LoginResponse;
import com.hana8.hanaro.mall.dto.UserDTO;
import com.hana8.hanaro.mall.entity.User;
import com.hana8.hanaro.mall.enums.Role;
import com.hana8.hanaro.mall.repository.UserRepository;
import com.hana8.hanaro.mall.security.JwtUtil;
import com.hana8.hanaro.mall.service.UserService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Tag(name = "인증", description = "로그인 및 회원가입 관련 API")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

    @PostMapping("/login")
    @Tag(name = "인증", description = "로그인")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
        );

        Map<String, Object> claims = jwtUtil.authenticationToClaims(authenticate);
        String accessToken = jwtUtil.generateToken(claims, 10); // 10분
        String refreshToken = jwtUtil.generateToken(claims, 60 * 24); // 24시간

        User user = userRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다."));

        return ResponseEntity.ok(LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .nickname(user.getNickname())
                .role(user.getRole().name())
                .build());
    }

    @PostMapping("/signup")
    @Tag(name = "인증", description = "일반 사용자 회원가입")
    public ResponseEntity<String> signup(@Valid @RequestBody UserDTO request) {
        userService.signup(request, Role.USER);
        return ResponseEntity.status(HttpStatus.CREATED).body("회원가입이 완료되었습니다.");
    }

    @PostMapping("/admin/signup")
    @Tag(name = "인증", description = "관리자 회원가입")
    public ResponseEntity<String> adminSignup(@Valid @RequestBody UserDTO request) {
        userService.signup(request, Role.ADMIN);
        return ResponseEntity.status(HttpStatus.CREATED).body("관리자 회원가입이 완료되었습니다.");
    }
}
