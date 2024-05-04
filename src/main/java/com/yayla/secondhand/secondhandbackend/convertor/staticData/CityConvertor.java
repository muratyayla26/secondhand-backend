package com.yayla.secondhand.secondhandbackend.convertor.staticData;

import com.yayla.secondhand.secondhandbackend.model.dto.staticData.CityDto;
import com.yayla.secondhand.secondhandbackend.model.entity.staticData.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityConvertor {
    CityDto convert(City city);

    City convert(CityDto cityDto);

}
