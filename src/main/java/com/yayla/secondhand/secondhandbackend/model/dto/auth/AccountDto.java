package com.yayla.secondhand.secondhandbackend.model.dto.auth;

import com.yayla.secondhand.secondhandbackend.model.dto.BaseDto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AccountDto extends BaseDto {

    private Long accountId;

    private String username;

    private String email;

    private Boolean isEmailConfirmed;
}
