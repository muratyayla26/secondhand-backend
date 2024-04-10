package com.yayla.secondhand.secondhandbackend.manager;

import com.yayla.secondhand.secondhandbackend.exception.BusinessException;
import com.yayla.secondhand.secondhandbackend.model.dto.auth.TokenRefreshDto;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.Account;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.AccountRole;
import com.yayla.secondhand.secondhandbackend.model.enumtype.RoleType;
import com.yayla.secondhand.secondhandbackend.model.request.auth.LoginRequest;
import com.yayla.secondhand.secondhandbackend.model.request.auth.SignupRequest;
import com.yayla.secondhand.secondhandbackend.model.request.auth.TokenRefreshRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import com.yayla.secondhand.secondhandbackend.model.response.auth.LoginResponse;
import com.yayla.secondhand.secondhandbackend.model.response.auth.TokenRefreshResponse;
import com.yayla.secondhand.secondhandbackend.service.security.AccountRoleService;
import com.yayla.secondhand.secondhandbackend.service.security.AccountService;
import com.yayla.secondhand.secondhandbackend.service.security.RefreshTokenService;
import com.yayla.secondhand.secondhandbackend.service.security.UserDetailsImpl;
import com.yayla.secondhand.secondhandbackend.system.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthManager {
    private static final String BEARER_PREFIX = "Bearer";
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final AccountService accountService;
    private final AccountRoleService accountRoleService;
    private final PasswordEncoder passwordEncoder;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        TokenRefreshDto tokenRefreshDto = refreshTokenService.createRefreshToken(userDetails.getAccountId());
        LoginResponse.LoginData loginData = new LoginResponse.LoginData(accessToken,
                tokenRefreshDto.getToken(),
                BEARER_PREFIX,
                userDetails.getAccountId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles);
        return new LoginResponse(loginData);
    }

    public BaseResponse registerUser(SignupRequest signupRequest) {
        if (accountService.checkUserExists(signupRequest.getEmail())) {
            throw new BusinessException("User already exists with this email address.");
        }

        Account account = new Account(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()));

        Set<String> strRoles = signupRequest.getRole();
        Set<AccountRole> roles = new HashSet<>();

        if (strRoles == null || strRoles.isEmpty()) {
            roles.add(accountRoleService.retrieve(RoleType.ROLE_USER));
        } else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "admin":
                        roles.add(accountRoleService.retrieve(RoleType.ROLE_ADMIN));
                        break;
                    case "mod":
                        roles.add(accountRoleService.retrieve(RoleType.ROLE_MODERATOR));
                        break;
                    default:
                        roles.add(accountRoleService.retrieve(RoleType.ROLE_USER));
                }
            });
        }

        account.setRoles(roles);
        accountService.createUser(account);
        return new BaseResponse();
    }

    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        String requestRefreshToken = request.getRefreshToken();
        TokenRefreshDto tokenRefreshDto = refreshTokenService.retrieve(requestRefreshToken);
        tokenRefreshDto = refreshTokenService.verifyExpiration(tokenRefreshDto);
        Account account = tokenRefreshDto.getAccount();

        String accessToken = jwtUtils.generateTokenFromEmail(account.getEmail());
        TokenRefreshResponse.TokenData tokenData = new TokenRefreshResponse.TokenData(accessToken,
                requestRefreshToken, BEARER_PREFIX);
        return new TokenRefreshResponse(tokenData);
    }
}
