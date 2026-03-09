package com.hana8.demo.mapper;

import org.mapstruct.Mapper;

import com.hana8.demo.dto.ReplyDTO;
import com.hana8.demo.entity.Reply;

@Mapper(componentModel = "spring")
public interface ReplyMapper {
	ReplyDTO toDTO(Reply reply);

	Reply toEntity(ReplyDTO dto);
}
