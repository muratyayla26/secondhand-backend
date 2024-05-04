package com.yayla.secondhand.secondhandbackend.convertor.staticData;

import com.yayla.secondhand.secondhandbackend.model.dto.staticData.DistrictDto;
import com.yayla.secondhand.secondhandbackend.model.entity.staticData.District;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DistrictConvertor {
    DistrictDto convert(District district);

    District convert(DistrictDto districtDto);

}
