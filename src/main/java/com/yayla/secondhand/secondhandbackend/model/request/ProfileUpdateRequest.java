package com.yayla.secondhand.secondhandbackend.model.request;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfileUpdateRequest {
    private String mobileNumber;

    private String address;
}
