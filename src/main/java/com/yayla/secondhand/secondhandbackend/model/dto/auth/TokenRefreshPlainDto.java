package com.yayla.secondhand.secondhandbackend.model.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class TokenRefreshPlainDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
}
