package com.yayla.secondhand.secondhandbackend.convertor.product;

import com.yayla.secondhand.secondhandbackend.model.request.ProductCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.vo.ProductCreateVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductRequestToVoConvertor {

    ProductCreateVo convert(ProductCreateRequest productCreateRequest);
}
