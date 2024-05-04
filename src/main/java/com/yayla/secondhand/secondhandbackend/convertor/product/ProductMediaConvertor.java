package com.yayla.secondhand.secondhandbackend.convertor.product;

import com.yayla.secondhand.secondhandbackend.model.entity.product.ProductMedia;
import com.yayla.secondhand.secondhandbackend.model.request.product.ProductImagesDeleteRequest;
import com.yayla.secondhand.secondhandbackend.model.vo.product.ProductImageVo;
import com.yayla.secondhand.secondhandbackend.model.vo.product.ProductImagesDeleteVo;
import org.springframework.stereotype.Component;

@Component
public class ProductMediaConvertor {
    public ProductMedia convert(ProductImageVo productImageVo, Long productId) {
        ProductMedia productMedia = new ProductMedia();
        productMedia.setMediaKey(productImageVo.getFileKey());
        productMedia.setProductId(productId);
        return productMedia;
    }

    ;

    public ProductImagesDeleteVo convert(ProductImagesDeleteRequest productImagesDeleteRequest) {
        ProductImagesDeleteVo productImagesDeleteVo = new ProductImagesDeleteVo();
        productImagesDeleteVo.setImageIds(productImagesDeleteRequest.getImageIds());
        return productImagesDeleteVo;
    }
}
