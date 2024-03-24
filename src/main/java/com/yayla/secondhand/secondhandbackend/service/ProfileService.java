package com.yayla.secondhand.secondhandbackend.service;

import com.yayla.secondhand.secondhandbackend.model.entity.Profile;
import com.yayla.secondhand.secondhandbackend.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class ProfileService {

    private ProfileRepository profileRepository;

    public Profile fetchProfile(Long profileId) {
        return profileRepository.findById(profileId).orElseThrow(
                () -> new RuntimeException("not found")
        );
    }

}
