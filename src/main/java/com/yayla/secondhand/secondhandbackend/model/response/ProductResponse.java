package com.yayla.secondhand.secondhandbackend.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yayla.secondhand.secondhandbackend.model.dto.product.ProductDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductResponse extends BaseResponse {

    @JsonProperty("data")
    private ProductDto productDto;
}
