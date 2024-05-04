package com.yayla.secondhand.secondhandbackend.service.mail;

import com.yayla.secondhand.secondhandbackend.exception.BusinessException;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.AccountConfirmationToken;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountMailService {

    private final MailService mailService;

    // TODO Async slow
    public void sendRegisterConfirmationMail(AccountConfirmationToken accountConfirmationToken, String to) {
        try {
            mailService.sendRegisterConfirmationMail(to, accountConfirmationToken.getToken());
        } catch (Exception e) {
            log.error("Failed to send confirmation mail", e);
            throw new BusinessException("Failed to send confirmation mail");
        }
    }
}
