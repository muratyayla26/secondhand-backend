package com.yayla.secondhand.secondhandbackend.service.security;

import com.yayla.secondhand.secondhandbackend.convertor.auth.TokenRefreshConvertor;
import com.yayla.secondhand.secondhandbackend.exception.AuthGeneralException;
import com.yayla.secondhand.secondhandbackend.model.dto.auth.TokenRefreshDto;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.Account;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.RefreshToken;
import com.yayla.secondhand.secondhandbackend.repository.security.RefreshTokenRepository;

import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${yayla.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final TokenRefreshConvertor tokenRefreshConvertor;
    private final AccountService accountService;

    public TokenRefreshDto retrieve(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(
                () -> new AuthGeneralException("Refresh token not found.")
        );
        return tokenRefreshConvertor.convert(refreshToken);
    }

    public TokenRefreshDto createRefreshToken(Long accountId) {
        RefreshToken refreshToken = new RefreshToken();
        Account account = accountService.retrieve(accountId);
        refreshToken.setAccount(account);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return tokenRefreshConvertor.convert(refreshToken);
    }

    public TokenRefreshDto verifyExpiration(TokenRefreshDto tokenRefreshDto) {
        if (tokenRefreshDto.getExpiryDate().compareTo(Instant.now()) < 0) {
            RefreshToken refreshToken = tokenRefreshConvertor.convert(tokenRefreshDto);
            refreshTokenRepository.delete(refreshToken);
            throw new AuthGeneralException("Refresh token was expired. Please make a new login request");
        }

        return tokenRefreshDto;
    }

    // TODO Auth overall
    @Transactional
    public int deleteByUserId(Long accountId) {
        Account account = accountService.retrieve(accountId);
        return refreshTokenRepository.deleteByAccount(account);
    }
}