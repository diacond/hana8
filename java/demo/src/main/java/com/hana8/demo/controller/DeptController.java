package com.hana8.demo.controller;

import com.hana8.demo.dto.DeptDTO;
import com.hana8.demo.service.DeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/depts")
@RequiredArgsConstructor
@Tag(name = "부서관리", description = "부서 상세~")
public class DeptController {

  private final DeptService service;

  @GetMapping("")
  List<DeptDTO> getDepts() {
    return service.getDepts();
  }

  @GetMapping("/{id}")
  @Tag(name = "부서 상세", description = "부서세부여행")
  @Operation(summary = "/api/depts/아이디넣어줘", description = "부서 id는 Integer입니다.")
  @Parameter(name = "id", description = "부서ID", example = "1")
  ResponseEntity<? extends Object> getDept(@PathVariable Integer id) { // 이거 물음표 왜 쓰더라?
    try {
      return ResponseEntity.ok(service.getDept(id));
    } catch (IllegalArgumentException e) {
      return ResponseEntity.status(404).body(e.getMessage());
    }
  }

  @PostMapping("")
  @Parameter(description = "부서", example = """
      {
                  "page": 1,
                  "size": 10,
                  "searchNickname": "",
                  "sortField": "id",
                  "sortDirection": "desc"
              }
      """)
  DeptDTO registDept(@Valid() @RequestBody DeptDTO dept) {
    return service.registDept(dept);
  }

  @PutMapping("/{id}")
  DeptDTO editDept(@PathVariable Integer id, @Valid() @RequestBody DeptDTO dept) {
    dept.setId(id);
    return service.editDept(dept);
  }

  @DeleteMapping("/{id}")
  int removeDept(@PathVariable Integer id) {
    return service.removeDept(id);
  }
}
