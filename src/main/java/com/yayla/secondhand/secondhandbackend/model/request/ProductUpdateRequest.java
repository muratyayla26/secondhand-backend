package com.yayla.secondhand.secondhandbackend.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductUpdateRequest {

    @NotNull
    private Long productId;
    private String title;
    private String description;
    private boolean isSold;
}
