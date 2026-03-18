package com.hana8.hanaro.mall.controller;

import com.hana8.hanaro.mall.dto.ProductDTO;
import com.hana8.hanaro.mall.enums.OnSale;
import com.hana8.hanaro.mall.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {

  private final ProductService productService;

  // ================= [Admin 전용 API] =================

  // 상품 등록 (과제 조건: JST-303/380 유효성 검사 적용)
  @PostMapping("/admin")
  public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
    // 실제로는 MultipartFile을 받아 서버나 S3에 저장하고 그 URL을 ProductDTO에 세팅하는 로직이 추가되어야 합니다.
    // 현재는 JSON으로 imagePath를 문자열로 받는다고 가정합니다.
    ProductDTO createdProduct = productService.createProduct(productDTO);
    return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
  }

  // 상품 삭제
  @DeleteMapping("/admin/{id}")
  public ResponseEntity<String> deleteProduct(@PathVariable Long id) {
    productService.deleteProduct(id);
    return ResponseEntity.ok("상품이 성공적으로 삭제되었습니다.");
  }

  // ================= [User & Admin 공통 API] =================

  // 전체 상품 목록 조회
  @GetMapping
  public ResponseEntity<List<ProductDTO>> getAllProducts() {
    List<ProductDTO> products = productService.getAllProducts();
    return ResponseEntity.ok(products);
  }

  // 상품 상세 조회
  @GetMapping("/{id}")
  public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
    ProductDTO product = productService.getProductById(id);
    return ResponseEntity.ok(product);
  }

  @PatchMapping("/{productId}/sale-status") // 주소도 조금 더 명확하게 sale-status로 변경
  public ResponseEntity<String> changeSaleStatus(
      @PathVariable Long productId,
      @RequestParam OnSale status) { // ProductStatus 대신 만드신 OnSale Enum 사용

    productService.changeSaleStatus(productId, status);
    return ResponseEntity.ok("상품 판매 여부가 " + status + "(으)로 변경되었습니다.");
  }


}
