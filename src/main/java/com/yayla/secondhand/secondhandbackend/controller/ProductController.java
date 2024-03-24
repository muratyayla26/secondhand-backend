package com.yayla.secondhand.secondhandbackend.controller;

import com.yayla.secondhand.secondhandbackend.model.dto.ProfileProductsDto;
import com.yayla.secondhand.secondhandbackend.model.entity.Product;
import com.yayla.secondhand.secondhandbackend.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/api/v1/product")
@AllArgsConstructor
public class ProductController {

    private final ProductService productService;

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public Product fetchProductDetail(@PathVariable Long productId) {
        // Request classı
        // multiple service case
        // productmanager.Fetchdetails
        return productService.fetchProduct(productId);

    }
    // product id / profile id
    @GetMapping("/profile/{profileId}")
    @ResponseStatus(HttpStatus.OK)
    public ProfileProductsDto fetchProfileProducts(@PathVariable Long profileId) {
        return productService.fetchProfileProducts(profileId);
    }
}
