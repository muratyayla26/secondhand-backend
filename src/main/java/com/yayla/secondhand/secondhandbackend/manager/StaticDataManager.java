package com.yayla.secondhand.secondhandbackend.manager;

import com.yayla.secondhand.secondhandbackend.model.dto.staticData.CityDto;
import com.yayla.secondhand.secondhandbackend.model.dto.staticData.DistrictDto;
import com.yayla.secondhand.secondhandbackend.model.response.staticData.CityResponse;
import com.yayla.secondhand.secondhandbackend.model.response.staticData.DistrictResponse;
import com.yayla.secondhand.secondhandbackend.service.StaticDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class StaticDataManager {

    private final StaticDataService staticDataService;

    public CityResponse fetchCities() {
        List<CityDto> cities = staticDataService.fetchCities();
        return mapCityResponse(cities);
    }

    public DistrictResponse fetchDistricts(Integer cityId) {
        List<DistrictDto> districts = staticDataService.fetchDistricts(cityId);
        return mapDistrictResponse(districts);
    }

    private CityResponse mapCityResponse(List<CityDto> cities) {
        CityResponse cityResponse = new CityResponse();
        cityResponse.setCities(cities);
        return cityResponse;
    }

    private DistrictResponse mapDistrictResponse(List<DistrictDto> districts) {
        DistrictResponse districtResponse = new DistrictResponse();
        districtResponse.setDistricts(districts);
        return districtResponse;
    }
}
