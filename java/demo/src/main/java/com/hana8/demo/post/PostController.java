package com.hana8.demo.post;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {

  private final PostService postService;

  // C: 게시글 작성 (POST /posts)
  @PostMapping
  public Post createPost(@RequestBody Post post) {
    return postService.createPost(post);
  }

  // R: 전체 조회 (GET /posts)
  @GetMapping
  public List<Post> getAllPosts() {
    return postService.getAllPosts();
  }

  // R: 단건 조회 (GET /posts/{id})
  @GetMapping("/{id}")
  public Post getPostById(@PathVariable("id") Long id) {
    return postService.getPostById(id);
  }

  // U: 게시글 수정 (PUT /posts/{id})
  @PutMapping("/{id}")
  public String updatePost(@PathVariable("id") Long id, @RequestBody Post post) {
    postService.updatePost(id, post);
    return id + "번 게시글이 수정되었습니다.";
  }

  // D: 게시글 삭제 (DELETE /posts/{id})
  @DeleteMapping("/{id}")
  public String deletePost(@PathVariable("id") Long id) {
    postService.deletePost(id);
    return id + "번 게시글이 삭제되었습니다.";
  }
}
