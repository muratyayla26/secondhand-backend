package com.yayla.secondhand.secondhandbackend.model.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CurrencyDto {

    private Integer currencyId;

    private String currencyName;

    private String currencySymbol;

    private String currencyCode;
}
