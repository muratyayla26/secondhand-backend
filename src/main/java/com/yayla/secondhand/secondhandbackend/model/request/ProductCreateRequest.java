package com.yayla.secondhand.secondhandbackend.model.request;

import com.yayla.secondhand.secondhandbackend.model.enumtype.ProductType;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProductCreateRequest {
    @NotBlank
    private String title;

    @NotBlank
    private String description;

    @NotNull
    private ProductType productType;

    @NotNull
    @Digits(integer = 10, fraction = 2)
    @DecimalMin(value = "0.00", inclusive = false)
    @DecimalMax(value = "9999999.99")
    private BigDecimal price;

    @NotNull
    private Integer currencyId;
}
