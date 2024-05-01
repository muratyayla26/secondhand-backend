package com.yayla.secondhand.secondhandbackend.service.security;

import com.yayla.secondhand.secondhandbackend.exception.AuthGeneralException;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.Account;
import com.yayla.secondhand.secondhandbackend.model.vo.auth.ChangePasswordVo;
import com.yayla.secondhand.secondhandbackend.repository.security.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Boolean checkUserExists(String email) {
        return accountRepository.existsByEmail(email);
    }

    public void createUser(Account account) {
        log.info("Register process has started, account email : {}", account.getEmail());
        accountRepository.save(account);
        log.info("Register process has ended, account email : {}", account.getEmail());
    }

    public Account retrieve(Long accountId) {
        return accountRepository.findById(accountId).orElseThrow(
                () -> new AuthGeneralException("Account not found"));
    }

    public void updatePassword(ChangePasswordVo changePasswordVo) {
        log.info("Password change has started, accountId : {}", changePasswordVo.getAccountId());
        Account account = this.retrieve(changePasswordVo.getAccountId());
        account.setPassword(changePasswordVo.getNewPassword());
        accountRepository.save(account);
        log.info("Password change has ended, accountId : {}", changePasswordVo.getAccountId());
    }
}
