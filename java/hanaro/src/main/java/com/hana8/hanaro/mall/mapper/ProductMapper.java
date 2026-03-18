package com.hana8.hanaro.mall.mapper;

import com.hana8.hanaro.mall.dto.ProductDTO;
import com.hana8.hanaro.mall.entity.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    ProductDTO toDTO(Product product);

    @Mapping(target = "onsale", ignore = true)
    @Mapping(target = "histories", ignore = true)
    Product toEntity(ProductDTO dto);
}
