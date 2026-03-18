package com.hana8.hanaro.mall.mapper;

import com.hana8.hanaro.mall.dto.AccountDTO;
import com.hana8.hanaro.mall.entity.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AccountMapper {

    @Mapping(target = "userId", source = "user.id")
    AccountDTO toDTO(Account account);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "history", ignore = true)
    Account toEntity(AccountDTO dto);
}
