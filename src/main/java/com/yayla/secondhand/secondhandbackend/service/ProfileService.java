package com.yayla.secondhand.secondhandbackend.service;

import com.yayla.secondhand.secondhandbackend.convertor.profile.ProfileConvertor;
import com.yayla.secondhand.secondhandbackend.exception.NotFoundException;
import com.yayla.secondhand.secondhandbackend.model.dto.ProfileDto;
import com.yayla.secondhand.secondhandbackend.model.entity.Profile;
import com.yayla.secondhand.secondhandbackend.model.vo.ProfileCreateVo;
import com.yayla.secondhand.secondhandbackend.model.vo.ProfileUpdateVo;
import com.yayla.secondhand.secondhandbackend.repository.ProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileConvertor profileConvertor;

    public Boolean checkProfileExists(Long accountId) {
        return profileRepository.existsByAccountId(accountId);
    }

    public ProfileDto fetchProfile(Long accountId) {
        return profileRepository.findByAccountId(accountId).map(profileConvertor::convert).orElseThrow(NotFoundException::new);
    }

    public ProfileDto createProfile(ProfileCreateVo profileCreateVo) {
        log.info("Profile creation has started. productCreateVo: {}", profileCreateVo.toString());
        Profile profile = profileConvertor.convert(profileCreateVo);
        Profile saved = profileRepository.save(profile);
        return profileConvertor.convert(saved);
    }

    public ProfileDto updateProfile(ProfileUpdateVo profileUpdateVo) {
        log.info("Profile update has started. profileUpdateVo: {}", profileUpdateVo.toString());
        Profile profile = profileRepository.findByAccountId(profileUpdateVo.getAccountId()).orElseThrow(NotFoundException::new);
        updateValues(profile, profileUpdateVo);
        Profile saved = profileRepository.save(profile);
        log.info("Profile update has ended. saved.getProfileId: {}", saved.getProfileId());
        return profileConvertor.convert(saved);
    }

    private void updateValues(Profile profile, ProfileUpdateVo profileUpdateVo) {
        Optional.of(profileUpdateVo).map(ProfileUpdateVo::getMobileNumber).ifPresent(profile::setMobileNumber);
        Optional.of(profileUpdateVo).map(ProfileUpdateVo::getAddress).ifPresent(profile::setAddress);
    }
}
