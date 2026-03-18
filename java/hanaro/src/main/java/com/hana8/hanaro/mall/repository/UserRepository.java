package com.hana8.hanaro.mall.repository;

import com.hana8.hanaro.mall.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

  // 로그인할 때 이메일로 회원 정보를 찾습니다.
  Optional<User> findByEmail(String email);

  // 회원가입할 때 이메일이 이미 존재하는지 확인합니다. (true/false)
  boolean existsByEmail(String email);

  // 회원가입할 때 닉네임이 이미 존재하는지 확인합니다. (true/false)
  boolean existsByNickname(String nickname);
}
