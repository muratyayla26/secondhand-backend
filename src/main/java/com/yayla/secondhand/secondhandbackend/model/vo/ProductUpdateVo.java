package com.yayla.secondhand.secondhandbackend.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductUpdateVo {

    private Long productId;
    private String title;
    private String description;
    private boolean isSold;
}
