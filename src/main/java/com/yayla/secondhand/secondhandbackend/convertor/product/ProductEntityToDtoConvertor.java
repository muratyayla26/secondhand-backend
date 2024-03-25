package com.yayla.secondhand.secondhandbackend.convertor.product;

import com.yayla.secondhand.secondhandbackend.model.dto.ProductDto;
import com.yayla.secondhand.secondhandbackend.model.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductEntityToDtoConvertor {

    ProductDto convert(Product product);
}
