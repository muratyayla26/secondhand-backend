package com.yayla.secondhand.secondhandbackend.repository.staticData;

import com.yayla.secondhand.secondhandbackend.model.entity.staticData.City;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends JpaRepository<City, Integer> {
    Optional<City> findByCityIdAndIsDeletedIsFalse(Integer cityId);

    List<City> findAllByIsDeletedIsFalse();
}
