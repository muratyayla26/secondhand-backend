package com.yayla.secondhand.secondhandbackend.model.entity.profile;

import jakarta.persistence.*;
import lombok.Getter;
import org.hibernate.annotations.Immutable;

import java.util.UUID;

@Entity
@Getter
@Immutable
@Table(name = "profile_plain_view")
public class ProfilePlain {

    @Id
    @Column(name = "profile_id")
    private Long profileId;

    @Column(name = "account_id")
    private Long accountId;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "profile_image_key")
    private UUID profileImageKey;
}
