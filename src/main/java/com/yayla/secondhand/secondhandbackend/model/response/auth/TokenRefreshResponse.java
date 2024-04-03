package com.yayla.secondhand.secondhandbackend.model.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yayla.secondhand.secondhandbackend.model.dto.auth.TokenRefreshDto;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TokenRefreshResponse extends BaseResponse {
    // TODO nasil data yapıcaz
    @JsonProperty("data")
    private TokenData data;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class TokenData {
        private String accessToken;
        private String refreshToken;
        private String tokenType = "Bearer";
    }
}