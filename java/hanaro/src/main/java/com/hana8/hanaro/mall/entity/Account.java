package com.hana8.hanaro.mall.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "accounts")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Account {

  // Account 엔티티 내부
  @ManyToOne(fetch = FetchType.LAZY) // 성능을 위해 지연 로딩 필수!
  @JoinColumn(name = "user_id")
  private User user;

  // 계좌를 통해 가입 내역을 바로   알 수 있도록 1:1 추가 (선택)
  @OneToOne(mappedBy = "account")
  private History history;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "account_number", nullable = false, unique = true, length = 13)
  private String accountNumber; // ###-####-#### 형식 저장

  @Column(nullable = false)
  private Long balance; // 잔액

  public void updateBalance(Long newBalance) {
    this.balance = newBalance;
  }
}
