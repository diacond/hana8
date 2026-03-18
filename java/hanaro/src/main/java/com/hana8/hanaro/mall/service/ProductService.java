package com.hana8.hanaro.mall.service;

import com.hana8.hanaro.mall.dto.ProductDTO;
import com.hana8.hanaro.mall.entity.Product;
import com.hana8.hanaro.mall.enums.OnSale;
import com.hana8.hanaro.mall.repository.ProductRepository;
import com.hana8.hanaro.mall.mapper.ProductMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ProductService {

  private final ProductRepository productRepository;
  private final ProductMapper productMapper;

  /**
   * [Admin] 1. 상품 등록
   * (이미지 업로드는 Controller에서 처리하고 이미지 경로(imagePath)를 DTO에 담아 넘겨준다고 가정)
   */
  @Transactional
  public ProductDTO createProduct(ProductDTO dto) {
    Product product = productMapper.toEntity(dto);

    Product savedProduct = productRepository.save(product);
    return productMapper.toDTO(savedProduct);
  }

  /**
   * [User & Admin 공통] 2. 전체 상품 목록 조회
   */
  public List<ProductDTO> getAllProducts() {
    return productRepository.findAll().stream()
        .map(productMapper::toDTO)
        .collect(Collectors.toList());
  }

  /**
   * [User & Admin 공통] 3. 상품 상세 조회
   */
  public ProductDTO getProductById(Long id) {
    Product product = productRepository.findById(id)
        .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 상품입니다."));
    return productMapper.toDTO(product);
  }

  /**
   * [Admin] 4. 상품 삭제
   */
  @Transactional
  public void deleteProduct(Long id) {
    if (!productRepository.existsById(id)) {
      throw new IllegalArgumentException("삭제하려는 상품이 존재하지 않습니다.");
    }
    productRepository.deleteById(id);
  }

  @Transactional
  public void changeSaleStatus(Long productId, OnSale status) {
    Product product = productRepository.findById(productId)
        .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다."));

    // 엔티티 내부의 비즈니스 메서드 호출
    product.updateOnSale(status);
  }
}
