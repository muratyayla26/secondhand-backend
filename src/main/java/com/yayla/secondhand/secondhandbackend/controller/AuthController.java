package com.yayla.secondhand.secondhandbackend.controller;

import com.yayla.secondhand.secondhandbackend.manager.AuthManager;
import com.yayla.secondhand.secondhandbackend.model.request.auth.LoginRequest;
import com.yayla.secondhand.secondhandbackend.model.request.auth.SignupRequest;
import com.yayla.secondhand.secondhandbackend.model.request.auth.TokenRefreshRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import com.yayla.secondhand.secondhandbackend.model.response.auth.LoginResponse;
import com.yayla.secondhand.secondhandbackend.model.response.auth.TokenRefreshResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthManager authManager;

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
        return authManager.authenticateUser(loginRequest);
    }

    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        return authManager.registerUser(signupRequest);
    }

    @PostMapping("/refresh-token")
    @ResponseStatus(HttpStatus.CREATED)
    public TokenRefreshResponse refreshToken(@Valid @RequestBody TokenRefreshRequest request) {
        return authManager.refreshToken(request);
    }

}