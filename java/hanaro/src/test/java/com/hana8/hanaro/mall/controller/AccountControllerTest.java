package com.hana8.hanaro.mall.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hana8.hanaro.mall.dto.response.MyDashboardResponse;
import com.hana8.hanaro.mall.service.AccountService;
import java.math.BigDecimal;
import java.util.Collections;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AccountController.class)
@WithMockUser
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc(addFilters = false)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private AccountService accountService;

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
    @DisplayName("사용자의 자산 대시보드를 조회한다")
    void getMyAccountDashboard() throws Exception {
        // given
        Long userId = 1L;
        MyDashboardResponse response = MyDashboardResponse.builder()
                .totalBalance(10000L)
                .totalInterest(new BigDecimal("100.00"))
                .accounts(Collections.emptyList())
                .build();
        given(accountService.getMyAssetDashboard(userId)).willReturn(response);

        // when & then
        mockMvc.perform(get("/api/accounts/my")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .param("userId", userId.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalBalance").value(10000L))
                .andExpect(jsonPath("$.totalInterest").value(100.00));
    }
}
