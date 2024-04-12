package com.yayla.secondhand.secondhandbackend.convertor.profile;

import com.yayla.secondhand.secondhandbackend.model.enumtype.GenderType;
import jakarta.persistence.AttributeConverter;

import java.util.Arrays;
import java.util.Optional;

public class GenderTypeConvertor implements AttributeConverter<GenderType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(GenderType attribute) {
        return Optional.of(attribute).map(GenderType::getValue).orElse(null);
    }

    @Override
    public GenderType convertToEntityAttribute(Integer dbData) {
        return Arrays.stream(GenderType.values())
                .filter(genderType -> genderType.getValue() == dbData)
                .findFirst()
                .orElse(null);
    }
}
