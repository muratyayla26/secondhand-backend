package com.yayla.secondhand.secondhandbackend.model.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yayla.secondhand.secondhandbackend.model.dto.auth.LoginDto;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import lombok.*;


@Getter
@Setter
public class LoginResponse extends BaseResponse {

    @JsonProperty("data")
    private LoginDto loginDto;
}
