package com.yayla.secondhand.secondhandbackend.model.dto;

import com.yayla.secondhand.secondhandbackend.model.enumtype.ProductType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString(callSuper = true)
public class ProductDto extends BaseDto {

    private Long productId;

    private String title;

    private String description;

    private boolean isSold;

    private ProductType productType;

    private Long ownerId;

}
