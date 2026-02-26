package com.hana8.demo.post;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public class PostRepository {

  // Map 대신 List(ArrayList)를 사용합니다.
  private static final List<Post> store = new ArrayList<>();
  private static long sequence = 0L;

  // 1. 저장 (Create)
  public Post save(Post post) {
    post.setId(++sequence);
    store.add(post); // List에 데이터 추가
    return post;
  }

  // 2. 단건 조회 (Read)
  public Post findById(Long id) {
    // List를 순회하면서 id가 일치하는 첫 번째 Post를 찾습니다. 없으면 null 반환.
    return store.stream()
        .filter(post -> post.getId().equals(id))
        .findFirst()
        .orElse(null);
  }

  // 3. 전체 조회 (Read)
  public List<Post> findAll() {
    return new ArrayList<>(store); // 원본 보호를 위해 복사본을 반환
  }

  // 4. 수정 (Update)
  public void update(Long id, Post updateParam) {
    Post findPost = findById(id);
    if (findPost != null) {
      findPost.setTitle(updateParam.getTitle());
      findPost.setContent(updateParam.getContent());
    }
  }

  // 5. 삭제 (Delete)
  public void delete(Long id) {
    // List에서 조건(id가 일치함)에 맞는 항목을 지웁니다.
    store.removeIf(post -> post.getId().equals(id));
  }
}
