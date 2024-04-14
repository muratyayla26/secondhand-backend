package com.yayla.secondhand.secondhandbackend.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileUpdateVo {
    private String mobileNumber;

    private String address;

    private Integer cityId;

    private Integer districtId;

    private Long accountId;
}
