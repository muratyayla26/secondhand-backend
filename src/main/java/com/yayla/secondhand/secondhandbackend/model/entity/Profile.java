package com.yayla.secondhand.secondhandbackend.model.entity;

import com.yayla.secondhand.secondhandbackend.convertor.profile.GenderTypeConvertor;
import com.yayla.secondhand.secondhandbackend.model.enumtype.GenderType;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "profile")
public class Profile extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Long profileId;

    @NotBlank
    @Column(name = "first_name")
    private String firstName;

    @NotBlank
    @Column(name = "last_name")
    private String lastName;

    @Column(name = "mobile_number")
    private String mobileNumber;

    @Column(name = "address")
    private String address;

    @Convert(converter = GenderTypeConvertor.class)
    @Column(name = "gender_type")
    private GenderType genderType;

    @Column(name = "account_id", unique = true)
    private Long accountId;

}
