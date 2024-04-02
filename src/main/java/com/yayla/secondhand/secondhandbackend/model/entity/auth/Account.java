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
@Table(name= "account",
uniqueConstraints = {
        @UniqueConstraint(columnNames = "email")
})
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "account_id")
    private Long accountId;

    @Column(name = "username")
    private String username;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    // TODO account_account_role base entity extend edememis oluyor
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "account_account_role",
            joinColumns = @JoinColumn(name = "account_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<AccountRole> roles = new HashSet<>();

    public Account(String username, String email, String password) {
        this.username = username;
        this.email = email;
        this.password = password;
    }
}

