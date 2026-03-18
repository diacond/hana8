package com.hana8.hanaro.mall.service;

import com.hana8.hanaro.mall.dto.LoginRequest;
import com.hana8.hanaro.mall.dto.LoginResponse;
import com.hana8.hanaro.mall.dto.UserDTO;
import com.hana8.hanaro.mall.entity.Account;
import com.hana8.hanaro.mall.entity.User;
import com.hana8.hanaro.mall.enums.Role;
import com.hana8.hanaro.mall.mapper.UserMapper;
import com.hana8.hanaro.mall.repository.AccountRepository;
import com.hana8.hanaro.mall.repository.UserRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserService {

  private final UserRepository userRepository;
  private final AccountRepository accountRepository;
  private final UserMapper userMapper;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void signup(UserDTO request, Role role) {

    // 1. 중복 검증
    if (userRepository.existsByEmail(request.getEmail())) {
      throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
    }
    if (userRepository.existsByNickname(request.getNickname())) {
      throw new IllegalArgumentException("이미 사용 중인 닉네임입니다.");
    }
    if (accountRepository.existsByAccountNumber(request.getAccountNumber())) {
      throw new IllegalArgumentException("이미 존재하는 계좌번호입니다. 다른 번호를 입력해주세요.");
    }

    // 2. 유저(User) 엔티티 생성 및 DB 저장
    User user = User.builder()
        .email(request.getEmail())
        .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화
        .nickname(request.getNickname())
        .role(role)
        .build();

    User savedUser = userRepository.save(user);

    // 3. 가입 즉시 '자유입출금 통장(Account)' 자동 생성 로직
    Account freeAccount = Account.builder()
        .user(savedUser)
        .accountNumber(request.getAccountNumber())
        .balance(0L)
        .build();

    accountRepository.save(freeAccount);
  }

  /**
   * [Admin] 전체 회원 목록 조회
   */
  public List<UserDTO> getAllUsers() {
    List<User> users = userRepository.findAll();
    return users.stream()
        .map(userMapper::toDTO)
        .collect(Collectors.toList());
  }
}
