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

    @Value("${yayla.app.jwtSecret}")
    private String jwtSecret;

    @Value("${yayla.app.jwtExpirationMs}")
    private String jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return generateTokenFromEmail(userDetails.getEmail());
    }

    public String generateTokenFromEmail(String email) {
        long jwtExpirationMsLong = Long.parseLong(jwtExpirationMs);
        Instant issuedAt = Instant.now();
        Instant expiration = issuedAt.plus(Duration.ofMillis(jwtExpirationMsLong));

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
            throw new MalformedJwtException("Invalid JWT token: " + e.getMessage());
        } catch(ExpiredJwtException e){
            throw new ExpiredJwtException(e.getHeader(), e.getClaims(), "JWT token is expired: " + e.getMessage());
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedJwtException("JWT token is unsupported: " + e.getMessage());
        } catch (IllegalArgumentException e){
            throw new IllegalArgumentException("JWT claims string is empty: " + e.getMessage());
        }
    }
}
