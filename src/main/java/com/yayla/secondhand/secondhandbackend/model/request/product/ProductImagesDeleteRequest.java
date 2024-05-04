package com.yayla.secondhand.secondhandbackend.model.request.product;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductImagesDeleteRequest {
    @NotEmpty
    List<Long> imageIds;
}
