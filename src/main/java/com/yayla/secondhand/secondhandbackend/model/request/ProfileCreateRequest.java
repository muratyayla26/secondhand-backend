package com.yayla.secondhand.secondhandbackend.model.request;

import com.yayla.secondhand.secondhandbackend.model.enumtype.GenderType;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
public class ProfileCreateRequest {

    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;

    private String mobileNumber;

    private String address;

    private Integer cityId;

    private Integer districtId;

    private GenderType genderType;

}
