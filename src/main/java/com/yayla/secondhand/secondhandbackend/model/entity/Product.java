package com.yayla.secondhand.secondhandbackend.model.entity;

import com.yayla.secondhand.secondhandbackend.convertor.product.ProductTypeConvertor;
import com.yayla.secondhand.secondhandbackend.model.enumtype.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "product")
public class Product extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "product_id")
    private Long productId;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Convert(converter = ProductTypeConvertor.class)
    @Column(name = "product_type")
    private ProductType productType;

    @Column(name = "is_sold")
    private boolean isSold;

    @Column(name = "owner_id")
    private Long ownerId;

    // TODO unidirectional muhabbeti, comment entitiyisi içinde productu tekrar getirmesin diye boyle yaptım
    @OneToMany
    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
    private List<Comment> comments = new ArrayList<>();
}
