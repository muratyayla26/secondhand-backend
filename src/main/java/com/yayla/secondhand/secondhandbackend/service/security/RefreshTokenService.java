package com.yayla.secondhand.secondhandbackend.service.security;

import com.yayla.secondhand.secondhandbackend.convertor.auth.TokenRefreshConvertor;
import com.yayla.secondhand.secondhandbackend.exception.AuthGeneralException;
import com.yayla.secondhand.secondhandbackend.model.dto.auth.TokenRefreshDto;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.Account;
import com.yayla.secondhand.secondhandbackend.system.jwt.JwtUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.RefreshToken;
import com.yayla.secondhand.secondhandbackend.repository.security.RefreshTokenRepository;

import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenRefreshConvertor tokenRefreshConvertor;
    private final AccountService accountService;
    private final JwtUtils jwtUtils;

    public RefreshToken retrieve(String token) {
        return refreshTokenRepository.findByTokenAndIsDeletedIsFalseAndIsRevokedIsFalse(token).orElseThrow(
                () -> new AuthGeneralException("Refresh token not found.")
        );
    }

    public TokenRefreshDto createRefreshToken(Long accountId) {
        RefreshToken refreshToken = new RefreshToken();
        Account account = accountService.retrieve(accountId);
        String userEmail = account.getEmail();
        String token = jwtUtils.generateRefreshToken(userEmail);
        refreshToken.setAccountId(account.getAccountId());
        refreshToken.setToken(token);
        refreshToken = refreshTokenRepository.save(refreshToken);
        return tokenRefreshConvertor.convert(refreshToken);
    }

    @Transactional
    public TokenRefreshDto createRefreshTokenAndRevoke(Long accountId, RefreshToken oldRefreshToken) {
        log.info("Refresh token creation and revoke process has started, accountId : {}", accountId);
        this.revokeRefreshToken(oldRefreshToken);
        return this.createRefreshToken(accountId);
    }

    public RefreshToken verifyExpiration(RefreshToken refreshToken) {
        if (!jwtUtils.validateJwtToken(refreshToken.getToken())) {
            this.revokeRefreshToken(refreshToken);
            throw new AuthGeneralException("Refresh token is not valid. Please make a new login request");
        }
        return refreshToken;
    }

    public void revokeRefreshToken(RefreshToken refreshToken) {
        refreshToken.setDeleted(true);
        refreshToken.setRevoked(true);
        refreshTokenRepository.save(refreshToken);
    }
}