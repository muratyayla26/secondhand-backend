package com.yayla.secondhand.secondhandbackend.manager;

import com.yayla.secondhand.secondhandbackend.convertor.auth.AccountConvertor;
import com.yayla.secondhand.secondhandbackend.exception.BusinessException;
import com.yayla.secondhand.secondhandbackend.model.dto.auth.AccountDto;
import com.yayla.secondhand.secondhandbackend.model.dto.auth.LoginDto;
import com.yayla.secondhand.secondhandbackend.model.dto.auth.TokenRefreshDto;
import com.yayla.secondhand.secondhandbackend.model.dto.auth.TokenRefreshPlainDto;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.Account;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.AccountConfirmationToken;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.AccountRole;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.RefreshToken;
import com.yayla.secondhand.secondhandbackend.model.enumtype.RoleType;
import com.yayla.secondhand.secondhandbackend.model.request.auth.LoginRequest;
import com.yayla.secondhand.secondhandbackend.model.request.auth.SignupRequest;
import com.yayla.secondhand.secondhandbackend.model.request.auth.TokenRefreshRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import com.yayla.secondhand.secondhandbackend.model.response.auth.LoginResponse;
import com.yayla.secondhand.secondhandbackend.model.response.auth.TokenRefreshResponse;
import com.yayla.secondhand.secondhandbackend.model.vo.auth.RegisterConfirmationVo;
import com.yayla.secondhand.secondhandbackend.service.security.*;
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

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
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
    private final AccountConfirmationService accountConfirmationService;
    private final AccountConvertor accountConvertor;

    public LoginResponse authenticateUser(LoginRequest loginRequest) {
        log.info("Login process has started, account email : {}", loginRequest.getEmail());
        this.validatePendingEmailConformation(loginRequest.getEmail());

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String accessToken = jwtUtils.generateJwtToken(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        TokenRefreshDto tokenRefreshDto = refreshTokenService.createRefreshToken(userDetails.getAccountId());
        LoginDto loginDto = this.buildLoginDto(accessToken, tokenRefreshDto, userDetails);

        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setLoginDto(loginDto);
        log.info("Login process has ended, account email : {}", loginRequest.getEmail());
        return loginResponse;
    }

    public BaseResponse registerUser(SignupRequest signupRequest) {
        log.info("Register process has started, account email : {}", signupRequest.getEmail());
        this.validateUserDoesNotExist(signupRequest.getEmail());

        AccountDto waitingAccount = accountService.findUserWaitsEmailConfirmation(signupRequest.getEmail());
        if (waitingAccount != null) {
            this.initializeConfirmation(waitingAccount);
        } else {
            this.createUserAndInitializeConfirmation(signupRequest);
        }

        return new BaseResponse();
    }

    public TokenRefreshResponse refreshToken(TokenRefreshRequest request) {
        log.info("Refresh token process has started.");
        String requestRefreshToken = request.getRefreshToken();
        RefreshToken refreshToken = refreshTokenService.retrieve(requestRefreshToken);
        refreshToken = refreshTokenService.verifyExpiration(refreshToken);

        Account account = refreshToken.getAccount();
        String accessToken = jwtUtils.generateJwtToken(account.getEmail());
        TokenRefreshDto newRefreshToken = refreshTokenService.createRefreshTokenAndRevoke(account.getAccountId(), refreshToken);

        TokenRefreshPlainDto tokenRefreshPlainDto = this.buildTokenRefreshPlainDto(accessToken, newRefreshToken.getToken());
        TokenRefreshResponse tokenRefreshResponse = new TokenRefreshResponse();
        tokenRefreshResponse.setTokenRefreshPlainDto(tokenRefreshPlainDto);
        log.info("Refresh token process has ended, accountId : {}", account.getAccountId());
        return tokenRefreshResponse;
    }

    public BaseResponse confirmUserEmail(UUID token) {
        log.info("Confirm account email has started. token : {}", token);
        AccountConfirmationToken confirmToken = accountConfirmationService.retrieveConfirmationToken(token);
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime expiryDate = confirmToken.getExpiryDate();
        if (expiryDate.isBefore(now)) {
            throw new BusinessException("Expired account confirmation token.");
        } else {
            accountService.confirmAccountEmail(confirmToken.getAccount());
        }
        return new BaseResponse();
    }

    private void validatePendingEmailConformation(String email) {
        AccountDto waitingAccount = accountService.findUserWaitsEmailConfirmation(email);
        if (waitingAccount != null) {
            throw new BusinessException("Confirmation email is sent. Please try again after confirmation.");
        }
    }

    private LoginDto buildLoginDto(String accessToken, TokenRefreshDto tokenRefreshDto, UserDetailsImpl userDetails) {
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return LoginDto.builder()
                .accessToken(accessToken)
                .refreshToken(tokenRefreshDto.getToken())
                .tokenType(BEARER_PREFIX)
                .accountId(userDetails.getAccountId())
                .username(userDetails.getUsername())
                .email(userDetails.getEmail())
                .roles(roles).build();
    }

    private void validateUserDoesNotExist(String email) {
        if (accountService.checkUserExists(email)) {
            throw new BusinessException("User already exists with this email address.");
        }
    }

    private void initializeConfirmation(AccountDto accountDto) {
        RegisterConfirmationVo registerConfirmationVo = accountConvertor.convert(accountDto);
        accountConfirmationService.initializeConfirmation(registerConfirmationVo);
    }

    private void createUserAndInitializeConfirmation(SignupRequest signupRequest) {
        Account account = new Account(signupRequest.getUsername(),
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword()),
                false);

        Set<String> strRoles = signupRequest.getRoles();
        Set<AccountRole> roles = strRoles.stream()
                .map(role -> switch (role) {
                    case "admin" -> accountRoleService.retrieve(RoleType.ROLE_ADMIN);
                    case "mod" -> accountRoleService.retrieve(RoleType.ROLE_MODERATOR);
                    default -> accountRoleService.retrieve(RoleType.ROLE_USER);
                })
                .collect(Collectors.toSet());
        account.setRoles(roles);

        AccountDto savedAccount = accountService.createUser(account);
        RegisterConfirmationVo registerConfirmationVo = accountConvertor.convert(savedAccount);
        accountConfirmationService.initializeConfirmation(registerConfirmationVo);
    }

    private TokenRefreshPlainDto buildTokenRefreshPlainDto(String accessToken, String refreshToken) {
        return TokenRefreshPlainDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .tokenType(BEARER_PREFIX).build();
    }
}
