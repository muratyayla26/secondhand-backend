package com.yayla.secondhand.secondhandbackend.model.response.auth;

import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenRefreshResponse extends BaseResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
}