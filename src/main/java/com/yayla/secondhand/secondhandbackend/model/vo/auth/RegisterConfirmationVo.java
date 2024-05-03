package com.yayla.secondhand.secondhandbackend.model.vo.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterConfirmationVo {

    private String email;
    private Long accountId;
}
