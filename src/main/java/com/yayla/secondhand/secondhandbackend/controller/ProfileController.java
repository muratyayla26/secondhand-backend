package com.yayla.secondhand.secondhandbackend.controller;

import com.yayla.secondhand.secondhandbackend.manager.ProfileManager;
import com.yayla.secondhand.secondhandbackend.model.request.ProfileCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.request.ProfileUpdateRequest;
import com.yayla.secondhand.secondhandbackend.model.response.ProfileResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileManager profileManager;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse fetchProfile(){
        return profileManager.fetchProfile();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProfileResponse createProfile(@Valid @RequestBody ProfileCreateRequest profileCreateRequest){
        return profileManager.createProfile(profileCreateRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProfileResponse updateProfile(@RequestBody ProfileUpdateRequest profileUpdateRequest) {
        return profileManager.updateProfile(profileUpdateRequest);
    }


}
