package com.yayla.secondhand.secondhandbackend.service.security;

import com.yayla.secondhand.secondhandbackend.exception.NotFoundException;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.AccountConfirmationToken;
import com.yayla.secondhand.secondhandbackend.model.vo.auth.RegisterConfirmationVo;
import com.yayla.secondhand.secondhandbackend.repository.security.AccountMailConfirmationRepository;
import com.yayla.secondhand.secondhandbackend.service.mail.AccountMailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountConfirmationService {

    private final AccountMailConfirmationRepository accountMailConfirmationRepository;
    private final AccountMailService accountMailService;
    // TODO minutes later
    // TODO project folder structure
    @Value("${secondhand.app.mailValidityHours}")
    private int validityInHours;

    public void initializeConfirmation(RegisterConfirmationVo registerConfirmationVo) {
        log.info("Register confirmation process has been started. accountId: {}", registerConfirmationVo.getAccountId());
        AccountConfirmationToken accountConfirmationToken = this.generateConfirmationToken(registerConfirmationVo);
        accountMailService.sendRegisterConfirmationMail(accountConfirmationToken, registerConfirmationVo.getEmail());
        log.info("Register confirmation process has been ended. accountId: {}", registerConfirmationVo.getAccountId());
    }

    private AccountConfirmationToken generateConfirmationToken(RegisterConfirmationVo registerConfirmationVo) {
        AccountConfirmationToken accountConfirmationToken = new AccountConfirmationToken();
        accountConfirmationToken.setAccountId(registerConfirmationVo.getAccountId());
        accountConfirmationToken.setToken(UUID.randomUUID());
        accountConfirmationToken.setExpiryDate(LocalDateTime.now().plusMinutes(validityInHours));
        return accountMailConfirmationRepository.save(accountConfirmationToken);
    }

    public AccountConfirmationToken retrieveConfirmationToken(UUID token) {
        log.info("Retrieve confirmation token has started. token: {}", token);
        return accountMailConfirmationRepository.findByToken(token).orElseThrow(NotFoundException::new);
    }
}
