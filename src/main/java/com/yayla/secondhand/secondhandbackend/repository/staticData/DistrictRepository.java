package com.yayla.secondhand.secondhandbackend.repository.staticData;

import com.yayla.secondhand.secondhandbackend.model.entity.staticData.District;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface DistrictRepository extends JpaRepository<District, Integer> {
    Optional<District> findByDistrictIdAndIsDeletedIsFalse(Integer districtId);

    List<District> findByCityIdAndIsDeletedIsFalse(Integer cityId);
}
