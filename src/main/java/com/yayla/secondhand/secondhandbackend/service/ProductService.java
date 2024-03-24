package com.yayla.secondhand.secondhandbackend.service;

import com.yayla.secondhand.secondhandbackend.model.dto.ProfileProductsDto;
import com.yayla.secondhand.secondhandbackend.model.entity.Product;
import com.yayla.secondhand.secondhandbackend.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductService {


    // repository yukarıya service entity dönüyor
    // service yukarıya entity değil dto veya vo dönmeli
    private ProductRepository productRepository;

    public Product fetchProduct(Long productId) {
        return productRepository.findById(productId).orElseThrow(
                () -> new RuntimeException("not found")
        );
    }


    public ProfileProductsDto fetchProfileProducts(Long profileId) {
       List<Product> products = productRepository.findAllByOwnerId(profileId);
       ProfileProductsDto profileProductsDto = new ProfileProductsDto();
       profileProductsDto.setProducts(products);

       return profileProductsDto;
    }
}
