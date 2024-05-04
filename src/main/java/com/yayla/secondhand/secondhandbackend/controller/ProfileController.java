package com.yayla.secondhand.secondhandbackend.controller;

import com.yayla.secondhand.secondhandbackend.manager.ProfileManager;
import com.yayla.secondhand.secondhandbackend.model.request.profile.ProfileCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.request.profile.ProfileUpdateRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import com.yayla.secondhand.secondhandbackend.model.response.ProfileResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileManager profileManager;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse fetchProfile() {
        return profileManager.fetchProfile();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse createProfile(@Valid @RequestBody ProfileCreateRequest profileCreateRequest) {
        return profileManager.createProfile(profileCreateRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse updateProfile(@RequestBody ProfileUpdateRequest profileUpdateRequest) {
        return profileManager.updateProfile(profileUpdateRequest);
    }

    @PostMapping(value = "/upload-image", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse uploadProfileImage(@RequestParam("file") MultipartFile file) {
        return profileManager.uploadProfileImage(file);
    }
}
