package com.hana8.demo.post;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

  private final PostRepository postRepository;

  @Override
  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  public Post getPostById(Long id) {

  }


}
