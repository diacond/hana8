package com.hana8.demo;

import com.hana8.demo.entity.Post;
import com.hana8.demo.repository.PostRepository;
import jakarta.annotation.Nullable;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class InitLoader implements ApplicationRunner {

  private final PostRepository postRepository;

  @Override
  public void run(@Nullable ApplicationArguments args) {
    postRepository.save(new Post("Title1", "Shin"));
    postRepository.save(new Post("Title2", "kim"));
    postRepository.save(new Post("Title3", "lee"));
  }
}
