package com.yayla.secondhand.secondhandbackend.service;

import com.yayla.secondhand.secondhandbackend.convertor.staticData.CityConvertor;
import com.yayla.secondhand.secondhandbackend.convertor.staticData.DistrictConvertor;
import com.yayla.secondhand.secondhandbackend.exception.NotFoundException;
import com.yayla.secondhand.secondhandbackend.model.dto.CityDto;
import com.yayla.secondhand.secondhandbackend.model.dto.DistrictDto;
import com.yayla.secondhand.secondhandbackend.repository.CityRepository;
import com.yayla.secondhand.secondhandbackend.repository.DistrictRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class StaticDataService {

    private final CityRepository cityRepository;
    private final DistrictRepository districtRepository;
    private final CityConvertor cityConvertor;
    private final DistrictConvertor districtConvertor;

    public CityDto fetchCity(Integer cityId){
        return cityRepository.findByCityIdAndIsDeletedIsFalse(cityId).map(cityConvertor::convert).orElseThrow(NotFoundException::new);
    }

    public DistrictDto fetchDistrict(Integer districtId){
        return districtRepository.findByDistrictIdAndIsDeletedIsFalse(districtId).map(districtConvertor::convert).orElseThrow(NotFoundException::new);
    }

    public List<CityDto> fetchCities() {
        return cityRepository.findAllByIsDeletedIsFalse().stream()
                .map(cityConvertor::convert)
                .collect(Collectors.toList());
    }

    public List<DistrictDto> fetchDistricts(Integer cityId) {
        return districtRepository.findByCityIdAndIsDeletedIsFalse(cityId).stream()
                .map(districtConvertor::convert)
                .collect(Collectors.toList());
    }

}
