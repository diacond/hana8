package com.hana8.hanaro.mall.repository;

import com.hana8.hanaro.mall.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {

  // 특정 회원의 예적금 가입 내역 전체 가져오기
  List<History> findAllByUserId(Long userId);

  // [관리자용] 닉네임이 정확히 일치하는 회원의 가입 내역 검색
  List<History> findAllByUser_Nickname(String nickname);
}
