package com.yayla.secondhand.secondhandbackend.model.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class TokenRefreshRequest {
    @NotBlank
    private String refreshToken;
}
