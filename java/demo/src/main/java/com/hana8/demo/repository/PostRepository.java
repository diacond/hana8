package com.hana8.demo.repository;

import com.hana8.demo.entity.Post;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

  List<Post> findByTitleStartingWith(String title);

  void findByIdBetween(long l, long l1);
}
