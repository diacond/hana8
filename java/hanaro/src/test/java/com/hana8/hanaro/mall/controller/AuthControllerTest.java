package com.hana8.hanaro.mall.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana8.hanaro.mall.dto.LoginRequest;
import com.hana8.hanaro.mall.entity.User;
import com.hana8.hanaro.mall.enums.Role;
import com.hana8.hanaro.mall.repository.UserRepository;
import com.hana8.hanaro.mall.security.JwtUtil;
import com.hana8.hanaro.mall.service.UserService;
import com.hana8.hanaro.mall.security.JwtAuthenticationFilter;
import com.hana8.hanaro.mall.security.CustomUserDetailsService;
import com.hana8.hanaro.mall.config.CustomSecurityConfig;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AuthController.class)
@Import(CustomSecurityConfig.class)
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockitoBean
    private AuthenticationManager authenticationManager;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @MockitoBean
    private JwtUtil jwtUtil;

    @MockitoBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private UserDetailsService userDetailsService;

    @Test
    @DisplayName("로그인을 수행한다")
    @org.springframework.security.test.context.support.WithMockUser
    void login() throws Exception {
        // given
        LoginRequest loginRequest = new LoginRequest("test@example.com", "password");
        Authentication auth = new UsernamePasswordAuthenticationToken("test@example.com", "password");
        User user = User.builder()
                .email("test@example.com")
                .nickname("tester")
                .role(Role.USER)
                .build();

        when(authenticationManager.authenticate(any())).thenReturn(auth);
        when(jwtUtil.authenticationToClaims(any())).thenReturn(Map.of("email", "test@example.com", "role", "USER"));
        when(jwtUtil.generateToken(any(), any(Integer.class))).thenReturn("mock-token");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));

        // when & then
        mockMvc.perform(post("/api/auth/login")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(loginRequest)))
                .andExpect(status().isOk());
    }
}
