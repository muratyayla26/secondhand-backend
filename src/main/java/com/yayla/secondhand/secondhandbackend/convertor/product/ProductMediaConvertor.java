package com.yayla.secondhand.secondhandbackend.convertor.product;

import com.yayla.secondhand.secondhandbackend.model.entity.ProductMedia;
import com.yayla.secondhand.secondhandbackend.model.vo.ProductImageVo;
import org.springframework.stereotype.Component;

@Component
public class ProductMediaConvertor {
    public ProductMedia convert(ProductImageVo productImageVo, Long productId) {
        ProductMedia productMedia = new ProductMedia();
        productMedia.setMediaKey(productImageVo.getFileKey());
        productMedia.setProductId(productId);
        return productMedia;
    };
}
