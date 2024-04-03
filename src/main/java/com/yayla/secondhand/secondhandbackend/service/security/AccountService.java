package com.yayla.secondhand.secondhandbackend.service.security;

import com.yayla.secondhand.secondhandbackend.model.entity.auth.Account;
import com.yayla.secondhand.secondhandbackend.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public Boolean checkUserExists(String email){
        return accountRepository.existsByEmail(email);
    }

    public void createUser(Account account) {
        accountRepository.save(account);
    }
}
