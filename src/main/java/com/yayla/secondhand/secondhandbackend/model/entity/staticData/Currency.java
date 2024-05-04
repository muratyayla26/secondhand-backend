package com.yayla.secondhand.secondhandbackend.model.entity.staticData;

import com.yayla.secondhand.secondhandbackend.model.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "currency")
public class Currency extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "currency_id")
    private Integer currencyId;

    @Column(name = "currency_name", nullable = false, unique = true)
    private String currencyName;

    @Column(name = "currency_symbol", nullable = false, unique = true)
    private String currencySymbol;

    @Column(name = "currency_code", nullable = false, unique = true)
    private String currencyCode;
}
