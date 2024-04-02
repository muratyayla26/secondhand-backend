package com.yayla.secondhand.secondhandbackend.controller;

import com.yayla.secondhand.secondhandbackend.exception.TokenRefreshException;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.Account;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.AccountRole;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.RefreshToken;
import com.yayla.secondhand.secondhandbackend.model.enumtype.RoleType;
import com.yayla.secondhand.secondhandbackend.model.request.auth.LoginRequest;
import com.yayla.secondhand.secondhandbackend.model.request.auth.SignupRequest;
import com.yayla.secondhand.secondhandbackend.model.request.auth.TokenRefreshRequest;
import com.yayla.secondhand.secondhandbackend.model.response.auth.LoginResponse;
import com.yayla.secondhand.secondhandbackend.model.response.MessageResponse;
import com.yayla.secondhand.secondhandbackend.model.response.auth.TokenRefreshResponse;
import com.yayla.secondhand.secondhandbackend.repository.AccountRepository;
import com.yayla.secondhand.secondhandbackend.repository.AccountRoleRepository;
import com.yayla.secondhand.secondhandbackend.service.security.RefreshTokenService;
import com.yayla.secondhand.secondhandbackend.service.security.UserDetailsImpl;
import com.yayla.secondhand.secondhandbackend.system.jwt.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    AccountRoleRepository accountRoleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    RefreshTokenService refreshTokenService;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String accessToken = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(userDetails.getAccountId());

        return ResponseEntity.ok(LoginResponse.builder()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken.getToken())
                        .accountId(userDetails.getAccountId())
                        .username(userDetails.getUsername())
                        .email(userDetails.getEmail())
                        .roles(roles)
                        .build());
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshtoken(@Valid @RequestBody TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();

        return refreshTokenService.findByToken(requestRefreshToken)
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getAccount)
                .map(account -> {
                    String accessToken = jwtUtils.generateTokenFromEmail(account.getEmail());
                    return ResponseEntity.ok(TokenRefreshResponse.builder()
                            .accessToken(accessToken)
                            .refreshToken(requestRefreshToken)
                            .build());
                })
                .orElseThrow(() -> new TokenRefreshException(requestRefreshToken,
                        "Refresh token is not in database!"));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        if (accountRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        Account account = new Account(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<AccountRole> roles = new HashSet<>();

        if (strRoles == null) {
            AccountRole accountRole = accountRoleRepository.findByRoleName(RoleType.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(accountRole);
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        AccountRole adminRole = accountRoleRepository.findByRoleName(RoleType.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);

                        break;
                    case "mod":
                        AccountRole modRole = accountRoleRepository.findByRoleName(RoleType.ROLE_MODERATOR)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(modRole);

                        break;
                    default:
                        AccountRole userRole = accountRoleRepository.findByRoleName(RoleType.ROLE_USER)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(userRole);
                }
            });
        }

        account.setRoles(roles);
        accountRepository.save(account);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}