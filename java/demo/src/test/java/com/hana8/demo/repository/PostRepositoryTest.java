package com.hana8.demo.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.hana8.demo.entity.Post;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;
import org.springframework.boot.jdbc.test.autoconfigure.AutoConfigureTestDatabase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ActiveProfiles;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@Rollback(false)
class PostRepositoryTest extends BaseRepositoryTest {

  @Autowired
  private PostRepository repository;

  private static Long id;
  private static long orgCnt = 0;

  @BeforeEach
  void setOrgCnt() {
    if (orgCnt == 0) {
      orgCnt = repository.count();
    }
  }

  @Test
  void pagingTest() {
    Sort sort = Sort.by("id").descending();
    Pageable pager = PageRequest.of(0, 10, sort);

    Page<Post> page1 = repository.findAll(pager);
    List<Post> posts = page1.getContent();

    System.out.println("posts = " + posts);
    System.out.println("page1.getTotalPages() = " + page1.getTotalPages());


    Page<Post> page3 = repository.findAll(page2.nextOrLastPageable());
    System.out.println("page3.getNumber() = " + page3.getNumber());

    assertThat(page3.getContent()).;
  }

  @Test
  void titleLikeTest(){
    List<Post> posts = repository.findByTitleStartingWith("Title8");
    System.out.println("posts = " + posts);
    posts.stream().map(Post::getTitle).forEach(System.out::println);
    assertThat(posts).isNotEmpty()
        .allSatisfy(p ->
          assertThat(p.getTitle()).startsWith("Title8")
        );
  }

  @Test
  void jpqlTest(){
    List<Post> byidBetween = repository.findByIdBetween(10L, 20L);
    byidBetween.stream().map(Post p -> p.getId() + " : " + p.get)
  }

  @Test
  @Order(1)
  void createTest() {
    Post post = new Post("Title 101", "writer 101");
    post.setBody("Body content 101");
    Post savedPost = repository.save(post);

    assertThat(savedPost.getId()).isNotNull();
    assertThat(savedPost.getTitle()).isEqualTo("Title 101");
    assertThat(savedPost.getWriter()).isEqualTo("writer 101");

    id = savedPost.getId(); // 다음 테스트를 위해 ID 저장
    System.out.println("savedPost = " + savedPost);
  }

  @Test
  @Order(2)
  void readTest() {
    // ID로 단건 조회
    Post foundPost = repository.findById(id).orElseThrow();
    assertThat(foundPost.getTitle()).isEqualTo("Title 101");
    assertThat(foundPost.getWriter()).isEqualTo("writer 101");

    // 전체 목록 조회
    List<Post> all = repository.findAll();
    assertThat(all.size()).isGreaterThan((int) orgCnt);
    System.out.println("Current total posts: " + all.size());
  }

  @Test
  @Order(3)
  void updateTest() {
    Post post = repository.findById(id).orElseThrow();
    post.setTitle("Updated Title 101");
    post.setBody("Updated Body content 101");

    Post updatedPost = repository.save(post);
    assertThat(updatedPost.getTitle()).isEqualTo("Updated Title 101");
    assertThat(updatedPost.getBody()).isEqualTo("Updated Body content 101");

    System.out.println("updatedPost = " + updatedPost);
  }

  @Test
  @Order(4)
  void deleteTest() {
    repository.deleteById(id);

    // 삭제 확인
    assertThat(repository.findById(id)).isEmpty();

    // 원래 카운트로 돌아왔는지 확인
    assertThat(repository.count()).isEqualTo(orgCnt);
    System.out.println("Delete successful. Post count back to: " + orgCnt);
  }
}
