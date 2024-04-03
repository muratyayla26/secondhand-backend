package com.yayla.secondhand.secondhandbackend.model.response.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse extends BaseResponse {
    // TODO nasil data yapıcaz
    @JsonProperty("data")
    private LoginData data;

    @Getter
    @Setter
    @AllArgsConstructor
    public static class LoginData {
        private String accessToken;
        private String refreshToken;
        private String tokenType = "Bearer";
        private Long accountId;
        private String username;
        private String email;
        private List<String> roles;
    }

}
