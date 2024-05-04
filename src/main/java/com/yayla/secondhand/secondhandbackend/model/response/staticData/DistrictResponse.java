package com.yayla.secondhand.secondhandbackend.model.response.staticData;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yayla.secondhand.secondhandbackend.model.dto.staticData.DistrictDto;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class DistrictResponse extends BaseResponse {

    @JsonProperty("data")
    private List<DistrictDto> districts;
}
