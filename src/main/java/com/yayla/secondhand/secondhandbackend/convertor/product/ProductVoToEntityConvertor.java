package com.yayla.secondhand.secondhandbackend.convertor.product;

import com.yayla.secondhand.secondhandbackend.model.entity.Product;
import com.yayla.secondhand.secondhandbackend.model.vo.ProductCreateVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductVoToEntityConvertor {
    Product convert(ProductCreateVo productCreateVo);
}
