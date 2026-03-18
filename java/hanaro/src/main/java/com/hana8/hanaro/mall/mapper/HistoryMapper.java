package com.hana8.hanaro.mall.mapper;

import com.hana8.hanaro.mall.dto.HistoryDTO;
import com.hana8.hanaro.mall.entity.History;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface HistoryMapper {

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "accountId", source = "account.id")
    @Mapping(target = "isCancelled", expression = "java(history.getStatus() == com.hana8.hanaro.mall.enums.Status.QUIT)")
    HistoryDTO toDTO(History history);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "product", ignore = true)
    @Mapping(target = "account", ignore = true)
    History toEntity(HistoryDTO dto);
}
