package com.hana8.demo.post;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor // 기본 생성자도 추가해 주면 좋습니다.
@Builder
public class Post {

  private Long id;        // 게시글 번호
  private String title;   // 제목
  private String content; // 내용
  private String author;  // 작성자
}
