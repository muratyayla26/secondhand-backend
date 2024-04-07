package com.yayla.secondhand.secondhandbackend.convertor.product;

import com.yayla.secondhand.secondhandbackend.model.enumtype.ProductType;
import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.Optional;

public class ProductTypeConvertor implements AttributeConverter<ProductType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(ProductType attribute) {
        return Optional.of(attribute).map(ProductType::getValue).orElse(null);
    }

    @Override
    public ProductType convertToEntityAttribute(Integer dbData) {
        return Arrays.stream(ProductType.values())
                .filter(productType -> productType.getValue() == dbData)
                .findFirst()
                .orElse(null);
    }
}
