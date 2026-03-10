package com.hana8.demo.mapper;

import com.hana8.demo.dto.DeptDTO;
import com.hana8.demo.dto.MemberDTO;
import com.hana8.demo.entity.Dept;
import com.hana8.demo.entity.Member;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface MemberMapper {
	// @Mapping(source = "nickname", target = "username")
	@Mapping(target = "passwd", ignore = true)
	MemberDTO toDTO(Member member);

	Member toEntity(MemberDTO dto);

	@Mapping(target = "memberCount", expression = "java(dept.getDeptMembers() != null ? dept.getDeptMembers().size() : 0)")
	@Mapping(target = "captain", ignore = true)
	@Mapping(target = "deptMembers", ignore = true)
	DeptDTO toDTO(Dept dept);

	List<DeptDTO> toDTOList(List<Dept> depts);
}
