package com.yayla.secondhand.secondhandbackend.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yayla.secondhand.secondhandbackend.model.dto.CityDto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class CityResponse extends BaseResponse {

    @JsonProperty("data")
    private List<CityDto> cities;
}
