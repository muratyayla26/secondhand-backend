package com.yayla.secondhand.secondhandbackend.repository;

import com.yayla.secondhand.secondhandbackend.model.entity.ProductMedia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface ProductMediaRepository extends JpaRepository<ProductMedia, Long> {
    Optional<ProductMedia> findProductMediaByMediaIdAndProductIdAndIsDeletedIsFalse(Long mediaId, Long productId);

    @Modifying(clearAutomatically = true, flushAutomatically = true)
    @Query("""
                update ProductMedia p set
                p.isDeleted=true
                where p.productId=:productId and p.isDeleted=false
            """)
    void removeAllProductsMediaByProductId(@Param("productId") Long productId);
}
