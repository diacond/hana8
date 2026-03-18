package com.hana8.hanaro.mall.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hana8.hanaro.mall.entity.Product;
import com.hana8.hanaro.mall.enums.OnSale;
import com.hana8.hanaro.mall.enums.ProductType;
import com.hana8.hanaro.mall.enums.Term;
import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    @DisplayName("상품을 저장하고 ID로 조회한다")
    void saveAndFindById() {
        // given
        Product product = Product.builder()
                .name("하나 적금")
                .type(ProductType.SAVINGS)
                .depositAmount(10000L)
                .term(Term.MONTHLY)
                .durationMonths(12)
                .maturityYield(new BigDecimal("3.5"))
                .cancellationYield(new BigDecimal("1.0"))
                .onsale(OnSale.YES)
                .build();

        // when
        Product savedProduct = productRepository.save(product);
        Optional<Product> found = productRepository.findById(savedProduct.getId());

        // then
        assertThat(found).isPresent();
        assertThat(found.get().getName()).isEqualTo("하나 적금");
        assertThat(found.get().getType()).isEqualTo(ProductType.SAVINGS);
    }
}
