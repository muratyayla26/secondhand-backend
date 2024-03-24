package com.yayla.secondhand.secondhandbackend.repository;

import com.yayla.secondhand.secondhandbackend.model.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByOwnerId(Long ownerId);
}
