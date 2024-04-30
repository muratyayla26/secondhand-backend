package com.yayla.secondhand.secondhandbackend.model.entity;

import com.yayla.secondhand.secondhandbackend.convertor.product.ProductTypeConvertor;
import com.yayla.secondhand.secondhandbackend.model.enumtype.ProductType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLRestriction;

import java.math.BigDecimal;
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

    @OneToMany
    @SQLRestriction("is_deleted = false")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private List<Comment> comments = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "account_id", insertable = false, updatable = false)
    private ProfilePlain profile;

    @OneToMany
    @SQLRestriction("is_deleted = false")
    @JoinColumn(name = "product_id", referencedColumnName = "product_id", insertable = false, updatable = false)
    private List<ProductMedia> productMedias = new ArrayList<>();

    @Column(name = "price", precision = 10, scale = 2)
    private BigDecimal price;

    @Column(name = "currency_id")
    private Integer currencyId;

    @ManyToOne
    @JoinColumn(name = "currency_id", referencedColumnName = "currency_id", insertable = false, updatable = false)
    private Currency currency;
}
