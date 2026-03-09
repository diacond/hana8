package com.hana8.demo.dto;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class PostDTO extends PostRequestDTO {
	@JsonManagedReference
	private PostBodyDTO body;

	@JsonManagedReference
	private java.util.List<ReplyDTO> replies;
}
