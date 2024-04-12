package com.yayla.secondhand.secondhandbackend.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yayla.secondhand.secondhandbackend.model.dto.ProfileDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProfileResponse extends BaseResponse {

    @JsonProperty("data")
    private ProfileDto profileDto;
}
