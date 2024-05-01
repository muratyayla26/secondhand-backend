package com.yayla.secondhand.secondhandbackend.model.vo.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordVo {

    private Long accountId;
    private String newPassword;
}
