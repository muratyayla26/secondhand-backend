package com.yayla.secondhand.secondhandbackend.convertor.staticData;

import com.yayla.secondhand.secondhandbackend.model.dto.DistrictDto;
import com.yayla.secondhand.secondhandbackend.model.entity.District;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistrictConvertor {
    DistrictDto convert(District district);

    District convert(DistrictDto districtDto);

}
