package com.yayla.secondhand.secondhandbackend.model.request;

import com.yayla.secondhand.secondhandbackend.model.enumtype.ProductType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
}
