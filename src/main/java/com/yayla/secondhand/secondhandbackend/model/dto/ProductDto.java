package com.yayla.secondhand.secondhandbackend.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yayla.secondhand.secondhandbackend.model.enumtype.ProductType;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@ToString(callSuper = true)
public class ProductDto extends BaseDto {

    private Long productId;

    private String title;

    private String description;

    private boolean isSold;

    private ProductType productType;

    @JsonIgnore
    private Long ownerId;

    private List<CommentDto> comments = new ArrayList<>();

    private ProfilePlainDto profile;

    private List<ProductMediaDto> productMedias = new ArrayList<>();

    private BigDecimal price;

    private CurrencyDto currency;

}
