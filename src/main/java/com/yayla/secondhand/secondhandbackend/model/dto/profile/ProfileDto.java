package com.yayla.secondhand.secondhandbackend.model.dto.profile;

import com.yayla.secondhand.secondhandbackend.model.dto.BaseDto;
import com.yayla.secondhand.secondhandbackend.model.dto.staticData.CityDto;
import com.yayla.secondhand.secondhandbackend.model.dto.staticData.DistrictDto;
import com.yayla.secondhand.secondhandbackend.model.enumtype.GenderType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Getter
@Setter
@ToString(callSuper = true)
public class ProfileDto extends BaseDto {

    private Long profileId;

    private String firstName;

    private String lastName;

    private String mobileNumber;

    private String address;

    private CityDto city;

    private DistrictDto district;

    private GenderType genderType;

    private Long accountId;

    private UUID profileImageKey;
}
