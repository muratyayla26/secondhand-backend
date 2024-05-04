package com.yayla.secondhand.secondhandbackend.model.entity.staticData;


import com.yayla.secondhand.secondhandbackend.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "district")
public class District extends BaseEntity {

    @Id
    @Column(name = "district_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int districtId;

    @Column(name = "district_name")
    private String districtName;

    @Column(name = "city_id")
    private int cityId;
}
