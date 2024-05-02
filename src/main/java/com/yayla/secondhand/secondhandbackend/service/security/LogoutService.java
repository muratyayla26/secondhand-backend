package com.yayla.secondhand.secondhandbackend.service.security;

import com.yayla.secondhand.secondhandbackend.model.entity.auth.RefreshToken;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService implements LogoutHandler {

    private final RefreshTokenService refreshTokenService;

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        String refreshTokenReq = request.getHeader("Refresh-Token");
        if (refreshTokenReq != null) {
            RefreshToken refreshToken = refreshTokenService.retrieve(refreshTokenReq);
            refreshTokenService.revokeRefreshToken(refreshToken);
            SecurityContextHolder.clearContext();
        } else {
            throw new BadCredentialsException("Invalid Refresh-Token");
        }
    }
}
