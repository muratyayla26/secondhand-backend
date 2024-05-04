package com.yayla.secondhand.secondhandbackend.model.entity.staticData;

import com.yayla.secondhand.secondhandbackend.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "city")
public class City extends BaseEntity {

    @Id
    @Column(name = "city_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cityId;

    @Column(name = "city_name")
    private String cityName;

}
