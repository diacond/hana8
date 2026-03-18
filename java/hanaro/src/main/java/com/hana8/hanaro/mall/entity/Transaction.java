package com.hana8.hanaro.mall.entity;

import com.hana8.hanaro.mall.enums.TransactionType;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "transactions")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  // 출금 계좌 (null일 경우 외부 입금)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "from_account_id")
  private Account fromAccount;

  // 입금 계좌 (null일 경우 외부 출금)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "to_account_id")
  private Account toAccount;

  @Column(nullable = false)
  private Long amount;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private TransactionType type;

  private String description;

  @Column(name = "created_at", nullable = false)
  private LocalDateTime createdAt;

  @Builder
  public Transaction(Account fromAccount, Account toAccount, Long amount, TransactionType type, String description, LocalDateTime createdAt) {
    this.fromAccount = fromAccount;
    this.toAccount = toAccount;
    this.amount = amount;
    this.type = type;
    this.description = description;
    this.createdAt = createdAt != null ? createdAt : LocalDateTime.now();
  }
}
