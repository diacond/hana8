package com.hana8.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hana8.demo.entity.Reply;

public interface ReplyRepository extends JpaRepository<Reply, Long> {
}
