package com.yayla.secondhand.secondhandbackend.model.response.auth;

import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
public class LoginResponse extends BaseResponse {
    private String accessToken;
    private String refreshToken;
    private String tokenType = "Bearer";
    private Long accountId;
    private String username;
    private String email;
    private List<String> roles;
}
