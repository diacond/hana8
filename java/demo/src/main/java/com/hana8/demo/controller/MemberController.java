package com.hana8.demo.controller;

import com.hana8.demo.dto.MemberDTO;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members")
public class MemberController {

  @GetMapping("")
  List<MemberDTO> getMembers() {
    return "members";
  }
}
