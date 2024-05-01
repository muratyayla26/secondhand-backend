package com.yayla.secondhand.secondhandbackend.model.entity.auth;

import com.yayla.secondhand.secondhandbackend.model.entity.BaseEntity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity(name = "refresh_token")
public class RefreshToken extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "refresh_token_id")
    private long refreshTokenId;

    @Column(name = "account_id")
    private Long accountId;

    @OneToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", nullable = false, insertable = false, updatable = false)
    private Account account;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "is_revoked")
    private boolean isRevoked;

}