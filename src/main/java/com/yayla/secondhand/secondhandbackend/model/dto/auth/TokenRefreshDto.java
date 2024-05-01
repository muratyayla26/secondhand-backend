package com.yayla.secondhand.secondhandbackend.model.dto.auth;

import com.yayla.secondhand.secondhandbackend.model.dto.BaseDto;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.Account;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class TokenRefreshDto extends BaseDto {
    private long refreshTokenId;
    private Long accountId;
    private Account account;
    private String token;
    private boolean isRevoked;
}
