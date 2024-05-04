package com.yayla.secondhand.secondhandbackend.controller;

import com.yayla.secondhand.secondhandbackend.manager.StaticDataManager;
import com.yayla.secondhand.secondhandbackend.model.response.staticData.CityResponse;

import com.yayla.secondhand.secondhandbackend.model.response.staticData.DistrictResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1")
@RequiredArgsConstructor
public class StaticDataController {

    private final StaticDataManager staticDataManager;

    @GetMapping("/cities")
    @ResponseStatus(HttpStatus.OK)
    public CityResponse fetchCities() {
        return staticDataManager.fetchCities();
    }

    @GetMapping("/districts/{cityId}")
    @ResponseStatus(HttpStatus.OK)
    public DistrictResponse fetchDistricts(@PathVariable Integer cityId) {
        return staticDataManager.fetchDistricts(cityId);
    }
}
