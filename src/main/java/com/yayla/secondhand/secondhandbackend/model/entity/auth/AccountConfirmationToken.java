package com.yayla.secondhand.secondhandbackend.model.entity.auth;

import com.yayla.secondhand.secondhandbackend.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "account_confirmation_token")
public class AccountConfirmationToken extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long tokenId;

    @Column(name = "token", nullable = false)
    private UUID token;

    @Column(name = "account_id", nullable = false)
    private Long accountId;

    @ManyToOne
    @JoinColumn(name = "account_id", referencedColumnName = "account_id", insertable = false, updatable = false)
    private Account account;

    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;
}
