package com.yayla.secondhand.secondhandbackend.manager;

import com.yayla.secondhand.secondhandbackend.convertor.profile.ProfileConvertor;
import com.yayla.secondhand.secondhandbackend.exception.BusinessException;
import com.yayla.secondhand.secondhandbackend.model.dto.CityDto;
import com.yayla.secondhand.secondhandbackend.model.dto.DistrictDto;
import com.yayla.secondhand.secondhandbackend.model.dto.ProfileDto;
import com.yayla.secondhand.secondhandbackend.model.request.ProfileCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.request.ProfileUpdateRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import com.yayla.secondhand.secondhandbackend.model.response.ProfileResponse;
import com.yayla.secondhand.secondhandbackend.model.vo.ProfileCreateVo;
import com.yayla.secondhand.secondhandbackend.model.vo.ProfileImageVo;
import com.yayla.secondhand.secondhandbackend.model.vo.ProfileUpdateVo;
import com.yayla.secondhand.secondhandbackend.service.ProfileService;
import com.yayla.secondhand.secondhandbackend.service.SessionInfoService;
import com.yayla.secondhand.secondhandbackend.service.StaticDataService;

import com.yayla.secondhand.secondhandbackend.system.utility.MediaHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileManager {

    private final ProfileService profileService;
    private final ProfileConvertor profileConvertor;
    private final SessionInfoService sessionInfoService;
    private final StaticDataService staticDataService;

    public ProfileResponse fetchProfile() {
        Long currentAccountId = sessionInfoService.currentAccountId();
        if (!profileService.checkProfileExists(currentAccountId)) {
            throw new BusinessException("Profile does not exists");
        }

        ProfileDto profileDto = profileService.fetchProfile(currentAccountId);
        return mapResponse(profileDto);
    }


    public BaseResponse createProfile(ProfileCreateRequest profileCreateRequest) {
        log.info("Profile creation has started. profileCreateRequest : {}", profileCreateRequest.toString());
        Long currentAccountId = sessionInfoService.currentAccountId();
        if (profileService.checkProfileExists(currentAccountId)) {
            throw new BusinessException("Profile already exists");
        }

        if (profileCreateRequest.getCityId() != null && profileCreateRequest.getCityId() > 0) {
            validateCityAndDistrict(profileCreateRequest.getCityId(), profileCreateRequest.getDistrictId());
        } else {
            profileCreateRequest.setDistrictId(null);
        }

        ProfileCreateVo profileCreateVo = profileConvertor.convert(profileCreateRequest, currentAccountId);
        profileService.createProfile(profileCreateVo);
        return new BaseResponse();
    }

    public ProfileResponse updateProfile(ProfileUpdateRequest profileUpdateRequest) {
        log.info("Profile update has started. profileUpdateRequest : {}", profileUpdateRequest.toString());
        Long currentAccountId = sessionInfoService.currentAccountId();
        if (!profileService.checkProfileExists(currentAccountId)) {
            throw new BusinessException("Profile does not exists");
        }

        CityAndDistrictDto cityAndDistrictDto = new CityAndDistrictDto(null, null);
        if (profileUpdateRequest.getCityId() != null && profileUpdateRequest.getCityId() > 0) {
            cityAndDistrictDto = validateCityAndDistrict(profileUpdateRequest.getCityId(), profileUpdateRequest.getDistrictId());
        } else {
            profileUpdateRequest.setDistrictId(null);
        }

        ProfileUpdateVo profileUpdateVo = profileConvertor.convert(profileUpdateRequest, currentAccountId);
        ProfileDto profileDto = profileService.updateProfile(profileUpdateVo);
        profileDto.setCity(cityAndDistrictDto.cityDto());
        profileDto.setDistrict(cityAndDistrictDto.districtDto());
        return mapResponse(profileDto);
    }

    private CityAndDistrictDto validateCityAndDistrict(Integer cityId, Integer districtId) {
        CityDto cityDto = staticDataService.fetchCity(cityId);

        if (districtId != null && districtId > 0) {
            DistrictDto districtDto = staticDataService.fetchDistrict(districtId);
            if (cityDto.getCityId() != districtDto.getCityId()) {
                throw new BusinessException("City and district does not match.");
            }
            return new CityAndDistrictDto(cityDto, districtDto);
        }
        return new CityAndDistrictDto(cityDto, null);
    }

    private ProfileResponse mapResponse(ProfileDto profileDto) {
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setProfileDto(profileDto);
        return profileResponse;
    }

    public BaseResponse uploadProfileImage(MultipartFile file) {
        log.info("Profile image upload has started.");
        MediaHelper.validateImageType(file);
        UUID fileKey = MediaHelper.generateImageKey();
        String bucketPath = MediaHelper.generateBucketPath(MediaHelper.PROFILE_BUCKET_FOLDER, fileKey);
        Long currentAccountId = sessionInfoService.currentAccountId();
        ProfileImageVo profileImageVo = profileConvertor.convert(currentAccountId, file, fileKey, bucketPath);
        profileService.changeProfileImage(profileImageVo);
        return new BaseResponse();
    }

    record CityAndDistrictDto(CityDto cityDto, DistrictDto districtDto) {
    }
}
