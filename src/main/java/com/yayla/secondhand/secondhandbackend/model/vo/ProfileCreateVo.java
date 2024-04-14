package com.yayla.secondhand.secondhandbackend.model.vo;

import com.yayla.secondhand.secondhandbackend.model.enumtype.GenderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileCreateVo {

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String address;

    private Integer cityId;

    private Integer districtId;

    private GenderType genderType;

    private Long accountId;
}
