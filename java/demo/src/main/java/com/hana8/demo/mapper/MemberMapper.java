package com.hana8.demo.mapper;

import com.hana8.demo.dto.DeptDTO;
import com.hana8.demo.dto.MemberDTO;
import com.hana8.demo.entity.Dept;
import com.hana8.demo.entity.Member;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface MemberMapper {

  // Member 상세보기 시 사용하는 상세 매퍼
  @Mapping(target = "passwd", ignore = true)
  @Mapping(target = "depts", qualifiedByName = "toSimpleDeptDTO")
  @Mapping(target = "ledDepts", qualifiedByName = "toSimpleDeptDTO")
  MemberDTO toDTO(Member member);

  // 부서 상세에서 '부서원'이나 '부서장'을 표시할 때 사용하는 단순 매퍼 (불필요한 정보 대거 제외)
  @Named("toSimpleMemberDTO")
  @Mapping(target = "passwd", ignore = true)
  @Mapping(target = "email", ignore = true)
  @Mapping(target = "bloodType", ignore = true)
  @Mapping(target = "createdAt", ignore = true)
  @Mapping(target = "updatedAt", ignore = true)
  @Mapping(target = "isActive", ignore = true)
  @Mapping(target = "depts", ignore = true)
  @Mapping(target = "ledDepts", ignore = true)
  @Mapping(target = "posts", ignore = true)
  @Mapping(target = "replyCount", ignore = true)
  MemberDTO toSimpleMemberDTO(Member member);

  Member toEntity(MemberDTO dto);

  // 부서 상세보기 시 사용하는 상세 매퍼
  @Mapping(target = "memberCount", expression = "java(dept.getDeptMembers() != null ? dept.getDeptMembers().size() : 0)")
  @Mapping(target = "captain", qualifiedByName = "toSimpleMemberDTO")
  @Mapping(target = "deptMembers", qualifiedByName = "toSimpleMemberDTO")
  DeptDTO toDTO(Dept dept);

  // 멤버 상세보기에서 '소속 부서'를 표시할 때 사용하는 단순 매퍼
  @Named("toSimpleDeptDTO")
  @Mapping(target = "captain", ignore = true)
  @Mapping(target = "deptMembers", ignore = true)
  @Mapping(target = "memberCount", expression = "java(dept.getDeptMembers() != null ? dept.getDeptMembers().size() : 0)")
  DeptDTO toSimpleDeptDTO(Dept dept);

  List<DeptDTO> toDTOList(List<Dept> depts);
}
