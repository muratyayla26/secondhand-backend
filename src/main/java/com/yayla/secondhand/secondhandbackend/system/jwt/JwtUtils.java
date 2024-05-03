package com.yayla.secondhand.secondhandbackend.system.jwt;

import com.yayla.secondhand.secondhandbackend.service.security.UserDetailsImpl;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Duration;
import java.time.Instant;
import java.util.Date;

@Slf4j
@Component
public class JwtUtils {

    @Value("${secondhand.app.jwtSecret}")
    private String jwtSecret;

    @Value("${secondhand.app.jwtExpirationMs}")
    private String jwtExpirationMs;

    @Value("${secondhand.app.jwtRefreshExpirationMs}")
    private String refreshTokenExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return generateTokenFromEmail(userDetails.getEmail(), jwtExpirationMs);
    }

    public String generateJwtToken(String email) {
        return generateTokenFromEmail(email, jwtExpirationMs);
    }

    public String generateRefreshToken(String email) {
        return generateTokenFromEmail(email, refreshTokenExpirationMs);
    }

    public String generateTokenFromEmail(String email, String expirationMs) {
        long expirationMsLong = Long.parseLong(expirationMs);
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plus(Duration.ofMillis(expirationMsLong));

        return Jwts.builder().subject(email)
                .issuedAt(Date.from(issuedAt))
                .expiration(Date.from(expiration))
                .signWith(key())
                .compact();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public String getEmailFromJwtToken(String token) {
        return Jwts.parser().verifyWith(key()).build().parseSignedClaims(token)
                .getPayload().getSubject();
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(authToken).getPayload();
            return true;
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty", e);
        }
        return false;
    }
}
