package com.hana8.hanaro.mall.repository;

import com.hana8.hanaro.mall.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
  // 추가 쿼리 없이 이대로 두시면 됩니다!
}
