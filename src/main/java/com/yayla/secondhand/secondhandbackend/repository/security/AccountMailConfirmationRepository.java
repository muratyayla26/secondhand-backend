package com.yayla.secondhand.secondhandbackend.repository.security;

import com.yayla.secondhand.secondhandbackend.model.entity.auth.AccountConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AccountMailConfirmationRepository extends JpaRepository<AccountConfirmationToken, Long> {

    Optional<AccountConfirmationToken> findByToken(UUID token);
}
