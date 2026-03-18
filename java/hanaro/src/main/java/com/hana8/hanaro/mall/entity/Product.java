package com.hana8.hanaro.mall.entity;

import com.hana8.hanaro.mall.enums.OnSale;
import com.hana8.hanaro.mall.enums.ProductType;
import com.hana8.hanaro.mall.enums.Term;
import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private ProductType type; // DEPOSIT(예금), SAVINGS(적금)

  @Column(name = "deposit_amount")
  private Long depositAmount;

  @Enumerated(EnumType.STRING)
  @Column(name = "payment_cycle")
  private Term term; // MONTH(월), WEEK(주)

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  @Builder.Default
  private OnSale onsale = OnSale.YES; // 기본값 판매 중

  // 상태 변경을 위한 메서드 (Setter 대신 비즈니스 메서드 사용)
  public void updateOnSale(OnSale status) {
    this.onsale = status;
  }

  @Column(name = "duration_months", nullable = false)
  private Integer durationMonths; // 가입 기간 (개월 수)

  @Column(name = "maturity_yield", precision = 5, scale = 2, nullable = false)
  private BigDecimal maturityYield; // 만기 수익률 (%)

  @Column(name = "cancellation_yield", precision = 5, scale = 2, nullable = false)
  private BigDecimal cancellationYield; // 중도 해지 수익률 (%)

  @Column(name = "image_path")
  private String imagePath; // 업로드된 대표 이미지 경로

  @OneToMany(mappedBy = "product") // History 엔티티에 있는 'product' 필드에 의해 매핑됨
  @Builder.Default
  private List<History> histories = new ArrayList<>();

}
