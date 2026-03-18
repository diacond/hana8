package com.hana8.hanaro.mall.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana8.hanaro.mall.dto.AccountDTO;
import com.hana8.hanaro.mall.dto.HistoryDTO;
import com.hana8.hanaro.mall.service.HistoryService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(HistoryController.class)
@WithMockUser
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc(addFilters = false)
class HistoryControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new com.fasterxml.jackson.datatype.jsr310.JavaTimeModule());

    @MockitoBean
    private HistoryService historyService;

    @MockitoBean
    private com.hana8.hanaro.mall.security.JwtUtil jwtUtil;

    @MockitoBean
    private com.hana8.hanaro.mall.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private com.hana8.hanaro.mall.security.CustomUserDetailsService customUserDetailsService;

    @MockitoBean
    private org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    @MockitoBean
    private org.springframework.security.authentication.AuthenticationManager authenticationManager;

    @Test
    @DisplayName("상품 가입을 요청한다")
    void subscribeProduct() throws Exception {
        // given
        Long productId = 1L;
        Long userId = 1L;
        AccountDTO accountDTO = AccountDTO.builder()
                .accountNumber("12345678901") // 11자리 숫자
                .build();
        HistoryDTO response = HistoryDTO.builder()
                .id(1L)
                .userId(userId)
                .productId(productId)
                .status("ACTIVE")
                .createdAt(LocalDateTime.now())
                .build();

        given(historyService.subscribeProduct(eq(userId), eq(productId), any(AccountDTO.class)))
                .willReturn(response);

        // when & then
        mockMvc.perform(post("/api/history/subscribe/{productId}", productId)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .param("userId", userId.toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(accountDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.status").value("ACTIVE"));
    }

    @Test
    @DisplayName("상품 중도 해지를 요청한다")
    void quitProduct() throws Exception {
        // given
        Long historyId = 1L;
        Long userId = 1L;

        // when & then
        mockMvc.perform(patch("/api/history/{historyId}/quit", historyId)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .param("userId", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(content().string("성공적으로 해지되었습니다. (가입내역 ID: 1)"));
    }
}
