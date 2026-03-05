package com.hana8.demo.repository;

import com.hana8.demo.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

//get붙은 애들은 프록시 객체 리턴하는 애들
public interface MemberRepository extends JpaRepository<Member, Long> {
  // 이러면 끝이고, 구현체는 jpa가 만든다.

}
