package com.hana8.hanaro.mall.dto;

import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class HistoryDTO {
  private Long id;
  private Long userId;      // 누가
  private Long productId;   // 어떤 상품에
  private Long accountId;   // 어떤 계좌로 가입했는지
  private LocalDateTime createdAt;
  private boolean isCancelled;
  private String status;
}
