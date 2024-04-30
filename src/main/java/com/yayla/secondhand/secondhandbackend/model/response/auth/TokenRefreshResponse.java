package com.yayla.secondhand.secondhandbackend.model.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yayla.secondhand.secondhandbackend.model.dto.auth.TokenRefreshPlainDto;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TokenRefreshResponse extends BaseResponse {

    @JsonProperty("data")
    private TokenRefreshPlainDto tokenRefreshPlainDto;
}