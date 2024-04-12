package com.yayla.secondhand.secondhandbackend.manager;

import com.yayla.secondhand.secondhandbackend.convertor.profile.ProfileConvertor;
import com.yayla.secondhand.secondhandbackend.exception.BusinessException;
import com.yayla.secondhand.secondhandbackend.model.dto.ProfileDto;
import com.yayla.secondhand.secondhandbackend.model.request.ProfileCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.request.ProfileUpdateRequest;
import com.yayla.secondhand.secondhandbackend.model.response.ProfileResponse;
import com.yayla.secondhand.secondhandbackend.model.vo.ProfileCreateVo;
import com.yayla.secondhand.secondhandbackend.model.vo.ProfileUpdateVo;
import com.yayla.secondhand.secondhandbackend.service.ProfileService;
import com.yayla.secondhand.secondhandbackend.service.SessionInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileManager {

    private final ProfileService profileService;
    private final ProfileConvertor profileConvertor;
    private final SessionInfoService sessionInfoService;

    public ProfileResponse fetchProfile(){
        Long currentAccountId = sessionInfoService.currentAccountId();
        if(!profileService.checkProfileExists(currentAccountId)){
            throw new BusinessException("Profile does not exists");
        }

        ProfileDto profileDto = profileService.fetchProfile(currentAccountId);
        return mapResponse(profileDto);
    }


    public ProfileResponse createProfile(ProfileCreateRequest profileCreateRequest){
        log.info("Profile creation has started. profileCreateRequest : {}", profileCreateRequest.toString());
        Long currentAccountId = sessionInfoService.currentAccountId();
        if(profileService.checkProfileExists(currentAccountId)){
            throw new BusinessException("Profile already exists");
        }

        ProfileCreateVo profileCreateVo = profileConvertor.convert(profileCreateRequest, currentAccountId);
        ProfileDto profileDto = profileService.createProfile(profileCreateVo);
        return mapResponse(profileDto);
    }

    public ProfileResponse updateProfile(ProfileUpdateRequest profileUpdateRequest){
        log.info("Profile update has started. profileUpdateRequest : {}", profileUpdateRequest.toString());
        Long currentAccountId = sessionInfoService.currentAccountId();
        if(!profileService.checkProfileExists(currentAccountId)){
            throw new BusinessException("Profile does not exists");
        }
        ProfileUpdateVo profileUpdateVo = profileConvertor.convert(profileUpdateRequest, currentAccountId);
        ProfileDto profileDto = profileService.updateProfile(profileUpdateVo);
        return mapResponse(profileDto);
    }

    private ProfileResponse mapResponse(ProfileDto profileDto) {
        ProfileResponse profileResponse = new ProfileResponse();
        profileResponse.setProfileDto(profileDto);
        return profileResponse;
    }
}
