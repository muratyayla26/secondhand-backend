package com.yayla.secondhand.secondhandbackend.repository.security;

import com.yayla.secondhand.secondhandbackend.model.entity.auth.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByAccountIdAndIsDeletedIsFalseAndIsEmailConfirmedIsTrue(Long accountId);

    Optional<Account> findByEmailAndIsDeletedIsFalseAndIsEmailConfirmedIsTrue(String email);

    Boolean existsByEmailAndIsDeletedIsFalseAndIsEmailConfirmedIsTrue(String email);

    Optional<Account> findByEmailAndIsDeletedIsFalseAndIsEmailConfirmedIsFalse(String email);
}
