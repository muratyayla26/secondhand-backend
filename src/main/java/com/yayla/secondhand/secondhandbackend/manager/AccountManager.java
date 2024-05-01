package com.yayla.secondhand.secondhandbackend.manager;

import com.yayla.secondhand.secondhandbackend.convertor.auth.ChangePasswordConvertor;
import com.yayla.secondhand.secondhandbackend.exception.BusinessException;
import com.yayla.secondhand.secondhandbackend.model.request.auth.ChangePasswordRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import com.yayla.secondhand.secondhandbackend.model.vo.auth.ChangePasswordVo;
import com.yayla.secondhand.secondhandbackend.service.security.AccountService;
import com.yayla.secondhand.secondhandbackend.service.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountManager {

    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final ChangePasswordConvertor changePasswordConvertor;

    public BaseResponse changePassword(ChangePasswordRequest request, Principal principal) {
        UserDetailsImpl userDetails = (UserDetailsImpl) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();
        log.info("Password change has started, accountId : {}", userDetails.getAccountId());

        if (!passwordEncoder.matches(request.getCurrentPassword(), userDetails.getPassword())) {
            throw new BusinessException("Password is not correct");
        }
        if (!request.getNewPassword().equals(request.getConfirmNewPassword())) {
            throw new BusinessException("Passwords do not match");
        }

        request.setNewPassword(passwordEncoder.encode(request.getNewPassword()));
        ChangePasswordVo changePasswordVo = changePasswordConvertor.convert(request, userDetails.getAccountId());
        accountService.updatePassword(changePasswordVo);
        return new BaseResponse();
    }
}
