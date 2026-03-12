package com.hana8.demo.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.hana8.demo.dto.MemberImageDTO;
import com.hana8.demo.entity.MemberImage;

@Mapper(componentModel = "spring")
public interface MemberImageMapper {
	MemberImageDTO toDTO(MemberImage entity);
	@Mapping(target = "member", ignore = true)
	MemberImage toEntity(MemberImageDTO dto);
	List<MemberImageDTO> toDTOList(List<MemberImage> entities);
	List<MemberImage> toEntityList(List<MemberImageDTO> dtos);
}
