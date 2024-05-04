package com.yayla.secondhand.secondhandbackend.repository.product;

import com.yayla.secondhand.secondhandbackend.model.entity.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByProductIdAndIsDeletedIsFalse(Long productId);
}
