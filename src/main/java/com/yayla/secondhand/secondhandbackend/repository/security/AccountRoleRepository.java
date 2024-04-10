package com.yayla.secondhand.secondhandbackend.repository.security;

import com.yayla.secondhand.secondhandbackend.model.entity.auth.AccountRole;
import com.yayla.secondhand.secondhandbackend.model.enumtype.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRoleRepository extends JpaRepository<AccountRole, Long> {
    Optional<AccountRole> findByRoleName(RoleType roleName);
}
