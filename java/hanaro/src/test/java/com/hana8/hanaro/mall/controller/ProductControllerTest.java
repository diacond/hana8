package com.hana8.hanaro.mall.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hana8.hanaro.mall.dto.ProductDTO;
import com.hana8.hanaro.mall.enums.ProductType;
import com.hana8.hanaro.mall.service.ProductService;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(ProductController.class)
@WithMockUser(roles = "ADMIN")
@org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc(addFilters = false)
class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @MockitoBean
    private ProductService productService;

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
    @DisplayName("새로운 상품을 등록한다")
    void createProduct() throws Exception {
        // given
        ProductDTO productDTO = ProductDTO.builder()
                .name("하나 적금")
                .type(ProductType.SAVINGS)
                .depositAmount(10000L)
                .durationMonths(12)
                .maturityYield(new BigDecimal("3.5"))
                .cancellationYield(new BigDecimal("1.0"))
                .build();
        given(productService.createProduct(any(ProductDTO.class))).willReturn(productDTO);

        // when & then
        mockMvc.perform(post("/api/products/admin")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(productDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("하나 적금"));
    }

    @Test
    @DisplayName("상품을 삭제한다")
    void deleteProduct() throws Exception {
        // when & then
        mockMvc.perform(delete("/api/products/admin/{id}", 1L)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(content().string("상품이 성공적으로 삭제되었습니다."));
    }

    @Test
    @DisplayName("전체 상품 목록을 조회한다")
    void getAllProducts() throws Exception {
        // given
        ProductDTO product = ProductDTO.builder().name("하나 적금").build();
        given(productService.getAllProducts()).willReturn(List.of(product));

        // when & then
        mockMvc.perform(get("/api/products")
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("하나 적금"));
    }

    @Test
    @DisplayName("상품 상세 정보를 조회한다")
    void getProduct() throws Exception {
        // given
        ProductDTO product = ProductDTO.builder().id(1L).name("하나 적금").build();
        given(productService.getProductById(1L)).willReturn(product);

        // when & then
        mockMvc.perform(get("/api/products/{id}", 1L)
                        .with(org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("하나 적금"));
    }
}
