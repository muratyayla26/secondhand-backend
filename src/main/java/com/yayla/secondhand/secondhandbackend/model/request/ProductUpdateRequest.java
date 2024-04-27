package com.yayla.secondhand.secondhandbackend.model.request;

import com.yayla.secondhand.secondhandbackend.model.enumtype.ProductType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProductUpdateRequest {

    @NotNull
    private Long productId;
    private String title;
    private String description;
    private ProductType productType;
    private boolean isSold;
    private BigDecimal price;
}
