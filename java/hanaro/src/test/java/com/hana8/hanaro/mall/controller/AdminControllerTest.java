package com.hana8.hanaro.mall.controller;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.hana8.hanaro.mall.dto.HistoryDTO;
import com.hana8.hanaro.mall.dto.UserDTO;
import com.hana8.hanaro.mall.service.AccountService;
import com.hana8.hanaro.mall.service.HistoryService;
import com.hana8.hanaro.mall.service.UserService;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(AdminController.class)
@WithMockUser(roles = "ADMIN")
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc(addFilters = false)
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @MockitoBean
    private AccountService accountService;

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
    @DisplayName("전체 회원 목록을 조회한다")
    void getAllUsers() throws Exception {
        // given
        UserDTO user = UserDTO.builder()
                .id(1L)
                .email("test@test.com")
                .nickname("testUser")
                .role("USER")
                .build();
        given(userService.getAllUsers()).willReturn(List.of(user));

        // when & then
        mockMvc.perform(get("/api/admin/users")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickname").value("testUser"))
                .andExpect(jsonPath("$[0].email").value("test@test.com"));
    }

    @Test
    @DisplayName("닉네임으로 사용자의 가입 내역을 검색한다")
    void searchUserHistories() throws Exception {
        // given
        HistoryDTO history = HistoryDTO.builder()
                .id(1L)
                .userId(1L)
                .productId(1L)
                .accountId(1L)
                .createdAt(LocalDateTime.now())
                .isCancelled(false)
                .status("ACTIVE")
                .build();
        given(historyService.getHistoriesByNickname("testUser")).willReturn(List.of(history));

        // when & then
        mockMvc.perform(get("/api/admin/users/search")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .param("nickname", "testUser"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].status").value("ACTIVE"));
    }
}
