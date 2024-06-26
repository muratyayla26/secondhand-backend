package com.yayla.secondhand.secondhandbackend.service;

import com.yayla.secondhand.secondhandbackend.convertor.profile.ProfileConvertor;
import com.yayla.secondhand.secondhandbackend.exception.BusinessException;
import com.yayla.secondhand.secondhandbackend.exception.NotFoundException;
import com.yayla.secondhand.secondhandbackend.model.dto.profile.ProfileDto;
import com.yayla.secondhand.secondhandbackend.model.entity.profile.Profile;
import com.yayla.secondhand.secondhandbackend.model.vo.profile.ProfileCreateVo;
import com.yayla.secondhand.secondhandbackend.model.vo.profile.ProfileImageVo;
import com.yayla.secondhand.secondhandbackend.model.vo.profile.ProfileUpdateVo;
import com.yayla.secondhand.secondhandbackend.repository.profile.ProfileRepository;
import com.yayla.secondhand.secondhandbackend.system.utility.MediaHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final ProfileConvertor profileConvertor;
    private final S3Service s3Service;

    public Boolean checkProfileExists(Long accountId) {
        return profileRepository.existsByAccountIdAndIsDeletedIsFalse(accountId);
    }

    public ProfileDto fetchProfile(Long accountId) {
        return profileRepository.findByAccountIdAndIsDeletedIsFalse(accountId).map(profileConvertor::convert).orElseThrow(NotFoundException::new);
    }

    public void createProfile(ProfileCreateVo profileCreateVo) {
        log.info("Profile creation has started. productCreateVo: {}", profileCreateVo.toString());
        Profile profile = profileConvertor.convert(profileCreateVo);
        Profile saved = profileRepository.save(profile);
        log.info("Profile creation has ended. profileId: {}", saved.getProfileId());
    }

    public ProfileDto updateProfile(ProfileUpdateVo profileUpdateVo) {
        log.info("Profile update has started. profileUpdateVo: {}", profileUpdateVo.toString());
        Profile profile = profileRepository.findByAccountIdAndIsDeletedIsFalse(profileUpdateVo.getAccountId()).orElseThrow(NotFoundException::new);
        updateValues(profile, profileUpdateVo);
        Profile saved = profileRepository.save(profile);
        log.info("Profile update has ended. saved.getProfileId: {}", saved.getProfileId());
        return profileConvertor.convert(saved);
    }

    @Transactional
    public void changeProfileImage(ProfileImageVo profileImageVo) {
        log.info("Profile image upload has started. profileImageVo: {}", profileImageVo.toString());
        Profile profile = profileRepository.findByAccountIdAndIsDeletedIsFalse(profileImageVo.getAccountId()).orElseThrow(NotFoundException::new);
        UUID currImageKey = profile.getProfileImageKey();
        try {
            s3Service.uploadFile(profileImageVo.getFile(), profileImageVo.getBucketPath());
            profile.setProfileImageKey(profileImageVo.getFileKey());
            profileRepository.save(profile);
            deleteProfileImageIfExists(currImageKey);
            log.info("Profile image upload has ended. profileImageVo: {}", profileImageVo.toString());
        } catch (Exception exception) {
            log.error(exception.getMessage(), exception);
            throw new BusinessException("Failed to upload file");
        }
    }

    private void deleteProfileImageIfExists(UUID currImageKey) {
        if (currImageKey != null) {
            log.info("Profile image deletion has been started. profileImageKey: {}", currImageKey);
            String bucketPath = MediaHelper.generateBucketPath(MediaHelper.PROFILE_BUCKET_FOLDER, currImageKey);
            s3Service.deleteFile(bucketPath);
            log.info("Profile image has been deleted. profileImageKey: {}", currImageKey);
        }
    }

    private void updateValues(Profile profile, ProfileUpdateVo profileUpdateVo) {
        Optional.of(profileUpdateVo).map(ProfileUpdateVo::getMobileNumber).ifPresent(profile::setMobileNumber);
        Optional.of(profileUpdateVo).map(ProfileUpdateVo::getAddress).ifPresent(profile::setAddress);
        profile.setCityId(profileUpdateVo.getCityId());
        profile.setDistrictId(profileUpdateVo.getDistrictId());
    }
}
