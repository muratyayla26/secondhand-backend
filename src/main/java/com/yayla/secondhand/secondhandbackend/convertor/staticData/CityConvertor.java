package com.yayla.secondhand.secondhandbackend.convertor.staticData;

import com.yayla.secondhand.secondhandbackend.model.dto.CityDto;
import com.yayla.secondhand.secondhandbackend.model.entity.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityConvertor {
    CityDto convert(City city);

    City convert(CityDto cityDto);

}
