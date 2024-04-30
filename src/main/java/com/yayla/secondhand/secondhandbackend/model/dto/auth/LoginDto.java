package com.yayla.secondhand.secondhandbackend.model.dto.auth;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class LoginDto {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long accountId;
    private String username;
    private String email;
    private List<String> roles;
}
