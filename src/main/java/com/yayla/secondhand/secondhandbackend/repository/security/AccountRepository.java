package com.yayla.secondhand.secondhandbackend.repository.security;

import com.yayla.secondhand.secondhandbackend.model.entity.auth.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountIdAndIsDeletedIsFalse(Long accountId);

    Optional<Account> findByEmailAndIsDeletedIsFalse(String email);

    Boolean existsByEmailAndIsDeletedIsFalse(String email);
}
