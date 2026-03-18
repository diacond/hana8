package com.hana8.hanaro.mall.repository;

import com.hana8.hanaro.mall.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

  // 계좌번호로 특정 계좌 찾기 (입금/출금 로직 등에 사용)
  Optional<Account> findByAccountNumber(String accountNumber);

  // 회원가입 / 상품 가입 시 희망 계좌번호 중복 확인
  boolean existsByAccountNumber(String accountNumber);

  // 특정 회원의 '모든 계좌(자유입출금 + 예적금)' 목록 가져오기
  List<Account> findAllByUserId(Long userId);

  // 사용자의 첫 번째 계좌(자유입출금)를 찾는 메서드
  default Account findFreeAccountByUserId(Long userId) {
    return findAllByUserId(userId).stream()
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("자유입출금 계좌를 찾을 수 없습니다."));
  }
}
