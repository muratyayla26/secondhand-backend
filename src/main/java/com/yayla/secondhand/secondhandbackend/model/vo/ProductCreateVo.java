package com.yayla.secondhand.secondhandbackend.model.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ProductCreateVo {

    private String title;

    private String description;

    private Long ownerId;
}
