package com.yayla.secondhand.secondhandbackend.model.dto;

import com.yayla.secondhand.secondhandbackend.model.entity.Product;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ProductsOfProfileDto {
    private List<Product> products;
}
