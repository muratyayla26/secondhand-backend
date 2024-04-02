package com.yayla.secondhand.secondhandbackend.model.entity.auth;

import com.yayla.secondhand.secondhandbackend.model.entity.BaseEntity;
import com.yayla.secondhand.secondhandbackend.model.enumtype.RoleType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@Table(name = "account_role")
public class AccountRole extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "role_id")
    private Long roleId;

    @Enumerated(EnumType.STRING)
    @Column(name = "role_name", length = 20)
    private RoleType roleName;

}
