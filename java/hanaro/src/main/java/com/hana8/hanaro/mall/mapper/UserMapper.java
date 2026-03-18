package com.hana8.hanaro.mall.mapper;

import com.hana8.hanaro.mall.dto.UserDTO;
import com.hana8.hanaro.mall.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "accountNumber", ignore = true)
    UserDTO toDTO(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "accounts", ignore = true)
    @Mapping(target = "histories", ignore = true)
    User toEntity(UserDTO dto);
}
