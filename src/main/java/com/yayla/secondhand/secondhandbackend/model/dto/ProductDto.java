package com.yayla.secondhand.secondhandbackend.model.dto;

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

    private Long ownerId;

}
