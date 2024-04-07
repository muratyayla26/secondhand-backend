package com.yayla.secondhand.secondhandbackend.convertor.product;

import com.yayla.secondhand.secondhandbackend.model.dto.ProductDto;
import com.yayla.secondhand.secondhandbackend.model.entity.Product;
import com.yayla.secondhand.secondhandbackend.model.request.ProductCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.request.ProductUpdateRequest;
import com.yayla.secondhand.secondhandbackend.model.vo.ProductCreateVo;
import com.yayla.secondhand.secondhandbackend.model.vo.ProductUpdateVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductConvertor {

    ProductDto convert(Product product);
    ProductCreateVo convert(ProductCreateRequest productCreateRequest, Long ownerId);
    ProductUpdateVo convert(ProductUpdateRequest productUpdateRequest);
    Product convert(ProductCreateVo productCreateVo);
}
