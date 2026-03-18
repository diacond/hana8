package com.hana8.hanaro.mall.entity;
import com.hana8.hanaro.mall.enums.Status;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "history")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class History {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 누가 가입했는가?
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

  // 어떤 상품에 가입했는가?
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "product_id", nullable = false)
  private Product product;

  // 이 상품 가입을 위해 생성된 계좌 (희망 계좌번호) [cite: 14]
  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "account_id", nullable = false)
  private Account account;

  // 이자 계산을 위한 가입일
  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  // 해지 여부 상태값 (중도 해지 기능)
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private Status status;

  public void updateStatus(Status status) {
    this.status = status;
  }
}
