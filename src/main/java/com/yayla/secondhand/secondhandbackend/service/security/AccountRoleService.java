package com.yayla.secondhand.secondhandbackend.service.security;

import com.yayla.secondhand.secondhandbackend.exception.NotFoundException;
import com.yayla.secondhand.secondhandbackend.model.entity.auth.AccountRole;
import com.yayla.secondhand.secondhandbackend.model.enumtype.RoleType;
import com.yayla.secondhand.secondhandbackend.repository.security.AccountRoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountRoleService {
    private final AccountRoleRepository accountRoleRepository;
    public AccountRole retrieve(RoleType roleType) {
        return accountRoleRepository.findByRoleName(roleType).orElseThrow(NotFoundException::new);
    }
}
