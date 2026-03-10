package com.hana8.demo.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeptDTO {
	private Long id;
	private String name;

	@JsonIgnoreProperties({"depts", "ledDepts", "posts"})
	private MemberDTO captain;

	@JsonIgnoreProperties({"depts", "ledDepts", "posts"})
	private List<MemberDTO> deptMembers;

	private Integer memberCount;
}
