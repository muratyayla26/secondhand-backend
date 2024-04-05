package com.yayla.secondhand.secondhandbackend.service.security;

import com.yayla.secondhand.secondhandbackend.convertor.auth.TokenRefreshDtoToEntityConvertor;
import com.yayla.secondhand.secondhandbackend.convertor.auth.TokenRefreshEntityToDtoConvertor;
import com.yayla.secondhand.secondhandbackend.exception.NotFoundException;
import com.yayla.secondhand.secondhandbackend.exception.TokenRefreshException;
import com.yayla.secondhand.secondhandbackend.model.dto.auth.TokenRefreshDto;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.RefreshToken;
import com.yayla.secondhand.secondhandbackend.repository.AccountRepository;
import com.yayla.secondhand.secondhandbackend.repository.RefreshTokenRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Ref;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RefreshTokenService {
    @Value("${yayla.app.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final AccountRepository accountRepository;
    private final TokenRefreshEntityToDtoConvertor tokenRefreshEntityToDtoConvertor;
    private final TokenRefreshDtoToEntityConvertor tokenRefreshDtoToEntityConvertor;

    public TokenRefreshDto retrieve(String token) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(token).orElseThrow(
                () -> new TokenRefreshException("Refresh token not found.")
        );
        return tokenRefreshEntityToDtoConvertor.convert(refreshToken);
    }

    public TokenRefreshDto createRefreshToken(Long accountId) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setAccount(accountRepository.findById(accountId).get());
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(UUID.randomUUID().toString());

        refreshToken = refreshTokenRepository.save(refreshToken);
        return tokenRefreshEntityToDtoConvertor.convert(refreshToken);
    }

    public TokenRefreshDto verifyExpiration(TokenRefreshDto tokenRefreshDto) {
        if (tokenRefreshDto.getExpiryDate().compareTo(Instant.now()) < 0) {
            RefreshToken refreshToken = tokenRefreshDtoToEntityConvertor.convert(tokenRefreshDto);
            refreshTokenRepository.delete(refreshToken);
            throw new TokenRefreshException("Refresh token was expired. Please make a new login request");
        }

        return tokenRefreshDto;
    }

    @Transactional
    public int deleteByUserId(Long userId) {
        return refreshTokenRepository.deleteByAccount(accountRepository.findById(userId).get());
    }
}