package com.hana8.demo.post;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;

  public Post createPost(Post post) {
    return postRepository.save(post);
  }

  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  public Post getPostById(Long id) {
    return postRepository.findById(id);
  }

  public void updatePost(Long id, Post post) {
    postRepository.update(id, post);
  }

  public void deletePost(Long id) {
    postRepository.delete(id);
  }
}
