package com.yayla.secondhand.secondhandbackend.model.vo.product;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

@Getter
@Setter
@ToString
public class ProductImagesDeleteVo {
    List<Long> imageIds;
}
