package com.yayla.secondhand.secondhandbackend.model.vo;

import com.yayla.secondhand.secondhandbackend.model.enumtype.ProductType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;

@Getter
@Setter
@ToString
public class ProductUpdateVo {

    private Long productId;
    private String title;
    private String description;
    private ProductType productType;
    private boolean isSold;
    private BigDecimal price;

}
