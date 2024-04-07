package com.yayla.secondhand.secondhandbackend.service;

import com.yayla.secondhand.secondhandbackend.exception.AuthGeneralException;
import com.yayla.secondhand.secondhandbackend.exception.BusinessException;
import com.yayla.secondhand.secondhandbackend.service.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SessionInfoService {

    public Long currentAccountId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication == null || !authentication.isAuthenticated()) {
            throw new AuthGeneralException("Unauthorized access. Please login first.");
        }

        if(authentication.getPrincipal() instanceof UserDetails) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            return userDetails.getAccountId();
        } else {
            throw new BusinessException("Unexpected principal type. Cannot retrieve account ID.");
        }
    }
}
