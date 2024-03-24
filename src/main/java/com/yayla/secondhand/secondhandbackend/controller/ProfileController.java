package com.yayla.secondhand.secondhandbackend.controller;

import com.yayla.secondhand.secondhandbackend.model.entity.Profile;
import com.yayla.secondhand.secondhandbackend.service.ProfileService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/v1/profile")
@AllArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    @GetMapping("/{profileId}")
    @ResponseStatus(HttpStatus.OK)
    public Profile fetchProfileDetail(@PathVariable Long profileId) {
        return profileService.fetchProfile(profileId);
    }


}
