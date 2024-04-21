package com.yayla.secondhand.secondhandbackend.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProfilePlainDto {

    private Long profileId;

    private String firstName;

    private String lastName;
}
