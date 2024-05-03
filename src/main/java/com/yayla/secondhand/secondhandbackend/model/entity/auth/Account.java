package com.yayla.secondhand.secondhandbackend.model.entity.auth;

import com.yayla.secondhand.secondhandbackend.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "account")
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "email", nullable = false)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_email_confirmed")
    private Boolean isEmailConfirmed;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<AccountRole> roles = new HashSet<>();

    public Account(String username, String email, String password, Boolean isEmailConfirmed) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.isEmailConfirmed = isEmailConfirmed;
    }
}

