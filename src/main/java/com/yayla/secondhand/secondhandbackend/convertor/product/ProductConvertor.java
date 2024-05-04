package com.yayla.secondhand.secondhandbackend.convertor.product;

import com.yayla.secondhand.secondhandbackend.model.dto.product.ProductDto;
import com.yayla.secondhand.secondhandbackend.model.entity.product.Product;
import com.yayla.secondhand.secondhandbackend.model.request.product.ProductCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.request.product.ProductUpdateRequest;
import com.yayla.secondhand.secondhandbackend.model.vo.product.ProductCreateVo;
import com.yayla.secondhand.secondhandbackend.model.vo.product.ProductUpdateVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductConvertor {

    ProductDto convert(Product product);

    ProductCreateVo convert(ProductCreateRequest productCreateRequest, Long ownerId);

    ProductUpdateVo convert(ProductUpdateRequest productUpdateRequest);

    Product convert(ProductCreateVo productCreateVo);
}
