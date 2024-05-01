package com.yayla.secondhand.secondhandbackend.convertor.product;

import com.yayla.secondhand.secondhandbackend.model.entity.ProductMedia;
import com.yayla.secondhand.secondhandbackend.model.request.ProductImagesDeleteRequest;
import com.yayla.secondhand.secondhandbackend.model.vo.ProductImageVo;
import com.yayla.secondhand.secondhandbackend.model.vo.ProductImagesDeleteVo;
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
