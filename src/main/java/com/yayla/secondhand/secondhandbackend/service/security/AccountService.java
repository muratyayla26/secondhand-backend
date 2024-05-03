package com.yayla.secondhand.secondhandbackend.service.security;

import com.yayla.secondhand.secondhandbackend.convertor.auth.AccountConvertor;
import com.yayla.secondhand.secondhandbackend.exception.AuthGeneralException;
import com.yayla.secondhand.secondhandbackend.model.dto.auth.AccountDto;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.Account;
import com.yayla.secondhand.secondhandbackend.model.vo.auth.ChangePasswordVo;
import com.yayla.secondhand.secondhandbackend.repository.security.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;
    private final AccountConvertor accountConvertor;

    public Boolean checkUserExists(String email) {
        return accountRepository.existsByEmailAndIsDeletedIsFalseAndIsEmailConfirmedIsTrue(email);
    }

    public AccountDto findUserWaitsEmailConfirmation(String email) {
        Optional<Account> accountOpt = accountRepository.findByEmailAndIsDeletedIsFalseAndIsEmailConfirmedIsFalse(email);
        if (accountOpt.isPresent()) {
            Account account = accountOpt.get();
            return accountConvertor.convert(account);
        }
        return null;
    }

    public AccountDto createUser(Account account) {
        log.info("Register process has started, account email : {}", account.getEmail());
        Account saved = accountRepository.save(account);
        log.info("Register process has ended, account email : {}", account.getEmail());
        return accountConvertor.convert(saved);
    }

    public Account retrieve(Long accountId) {
        return accountRepository.findByAccountIdAndIsDeletedIsFalseAndIsEmailConfirmedIsTrue(accountId).orElseThrow(
                () -> new AuthGeneralException("Account not found"));
    }

    public void updatePassword(ChangePasswordVo changePasswordVo) {
        log.info("Password change has started, accountId : {}", changePasswordVo.getAccountId());
        Account account = this.retrieve(changePasswordVo.getAccountId());
        account.setPassword(changePasswordVo.getNewPassword());
        accountRepository.save(account);
        log.info("Password change has ended, accountId : {}", changePasswordVo.getAccountId());
    }

    public void confirmAccountEmail(Account account) {
        log.info("Confirm account email has started. accountId : {}", account.getAccountId());
        account.setIsEmailConfirmed(true);
        accountRepository.save(account);
        log.info("Confirm account email has finished. accountId : {}", account.getAccountId());
    }
}
