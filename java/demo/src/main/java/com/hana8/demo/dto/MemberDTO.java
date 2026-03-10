package com.hana8.demo.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.hana8.demo.common.enums.BloodType;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberDTO {
	@NotNull(groups = MemberDTO.OnUpdate.class, message = "수정할 멤버의 id를 입력하세요!")
	private Long id;

	@NotBlank
	private String nickname;

	@Email
	@NotBlank
	private String email;

	@Size(min = 8, max = 16)
	private String passwd;

	// @JsonProperty("bt")
	private BloodType bloodType;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime createdAt;

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
	private LocalDateTime updatedAt;

	private Boolean isActive;

	@JsonIgnoreProperties("writer")
	private List<PostDTO> posts;
	
	private Long replyCount;

	@JsonIgnoreProperties({"captain", "deptMembers"})
	private List<DeptDTO> depts;

	@JsonIgnoreProperties({"captain", "deptMembers"})
	private List<DeptDTO> ledDepts;

	public interface OnCreate {
	}

	public interface OnUpdate {
	}
}
