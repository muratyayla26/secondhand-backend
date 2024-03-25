package com.yayla.secondhand.secondhandbackend.convertor.product;

import com.yayla.secondhand.secondhandbackend.model.request.ProductUpdateRequest;
import com.yayla.secondhand.secondhandbackend.model.vo.ProductUpdateVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductUpdateRequestToVoConvertor {
    ProductUpdateVo convert(ProductUpdateRequest productUpdateRequest);
}
