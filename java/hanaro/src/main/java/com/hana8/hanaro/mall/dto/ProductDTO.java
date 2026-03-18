package com.hana8.hanaro.mall.dto;

import com.hana8.hanaro.mall.enums.ProductType;
import com.hana8.hanaro.mall.enums.Term;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

  private Long id; // 응답용

  @NotBlank(message = "상품명은 필수입니다.")
  private String name;

  @NotNull(message = "상품 종류(예금/적금)를 선택해주세요.")
  private ProductType type;

  @Min(value = 1000, message = "납입 금액은 최소 1,000원 이상이어야 합니다.")
  private Long depositAmount;

  private Term term;

  @NotNull(message = "가입 기간은 필수입니다.")
  @Min(value = 1, message = "가입 기간은 최소 1개월 이상이어야 합니다.")
  private Integer durationMonths;

  @NotNull(message = "만기 수익률은 필수입니다.")
  private BigDecimal maturityYield;

  @NotNull(message = "중도 해지 수익률은 필수입니다.")
  private BigDecimal cancellationYield;

  private String imagePath;
}
