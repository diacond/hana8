package com.hana8.demo.repository;

import com.hana8.demo.entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository extends JpaRepository<Dept, Long> {

}
