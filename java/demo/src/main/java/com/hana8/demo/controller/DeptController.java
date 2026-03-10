package com.hana8.demo.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hana8.demo.dto.DeptDTO;
import com.hana8.demo.mapper.MemberMapper;
import com.hana8.demo.repository.DeptRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/depts")
@RequiredArgsConstructor
public class DeptController {
	private final DeptRepository repository;
	private final MemberMapper mapper;

	@GetMapping("")
	public List<DeptDTO> getDepts() {
		return repository.findAll().stream().map(mapper::toDTO).toList();
	}

	@GetMapping("/{id}")
	public DeptDTO getDept(@PathVariable Long id) {
		return repository.findById(id).map(mapper::toDTO)
			.orElseThrow(() -> new IllegalArgumentException("Dept not found!"));
	}
}
