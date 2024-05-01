package com.yayla.secondhand.secondhandbackend.controller;

import com.yayla.secondhand.secondhandbackend.manager.AccountManager;
import com.yayla.secondhand.secondhandbackend.model.request.auth.ChangePasswordRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping(path = "/api/v1/account")
@RequiredArgsConstructor
public class AccountController {

    private final AccountManager accountManager;

    @PostMapping(value = "/change-password")
    @ResponseStatus(HttpStatus.OK)
    public BaseResponse changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest,
                                       Principal principal) {
        return accountManager.changePassword(changePasswordRequest, principal);
    }

}
