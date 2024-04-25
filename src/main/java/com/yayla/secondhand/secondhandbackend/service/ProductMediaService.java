package com.yayla.secondhand.secondhandbackend.service;

import com.yayla.secondhand.secondhandbackend.convertor.product.ProductMediaConvertor;
import com.yayla.secondhand.secondhandbackend.model.entity.ProductMedia;
import com.yayla.secondhand.secondhandbackend.model.vo.ProductImageVo;
import com.yayla.secondhand.secondhandbackend.repository.ProductMediaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductMediaService {

    private final ProductMediaRepository productMediaRepository;
    private final S3Service s3Service;
    private final ProductMediaConvertor productMediaConvertor;

    @Transactional
    public List<String> uploadProductImages(Long productId, List<ProductImageVo> productImageVos) {
        log.info("Product's image upload has started. productId: {}", productId);
        List<String> failedImages = new ArrayList<>();
        for (ProductImageVo productImage : productImageVos) {
            try {
                s3Service.uploadFile(productImage.getFile(), productImage.getBucketPath());
                ProductMedia productMedia = productMediaConvertor.convert(productImage, productId);
                productMediaRepository.save(productMedia);
            } catch (Exception exception) {
                log.error(exception.getMessage(), exception);
                failedImages.add(productImage.getOriginalFileName());
            }
        }
        log.info("Product's image upload has ended. productId: {}", productId);
        return failedImages;
    }
}
