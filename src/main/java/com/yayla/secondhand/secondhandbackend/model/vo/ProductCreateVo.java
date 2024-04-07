package com.yayla.secondhand.secondhandbackend.model.vo;

import com.yayla.secondhand.secondhandbackend.model.enumtype.ProductType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductCreateVo {

    private String title;

    private String description;

    private ProductType productType;

    private Long ownerId;
}
