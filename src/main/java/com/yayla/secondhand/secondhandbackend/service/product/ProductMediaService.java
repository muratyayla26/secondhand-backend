package com.yayla.secondhand.secondhandbackend.service.product;

import com.yayla.secondhand.secondhandbackend.convertor.product.ProductMediaConvertor;
import com.yayla.secondhand.secondhandbackend.exception.NotFoundException;
import com.yayla.secondhand.secondhandbackend.model.dto.product.ProductDto;
import com.yayla.secondhand.secondhandbackend.model.dto.product.ProductMediaDto;
import com.yayla.secondhand.secondhandbackend.model.entity.product.ProductMedia;
import com.yayla.secondhand.secondhandbackend.model.vo.product.ProductImageVo;
import com.yayla.secondhand.secondhandbackend.model.vo.product.ProductImagesDeleteVo;
import com.yayla.secondhand.secondhandbackend.repository.product.ProductMediaRepository;
import com.yayla.secondhand.secondhandbackend.service.S3Service;
import com.yayla.secondhand.secondhandbackend.system.utility.MediaHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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

    @Transactional
    public void deleteProductImages(Long productId, ProductImagesDeleteVo productImagesDeleteVo) {
        log.info("Product's image delete has started. productId: {}", productId);
        for (Long imageId : productImagesDeleteVo.getImageIds()) {
            ProductMedia productMedia = productMediaRepository.findProductMediaByMediaIdAndProductIdAndIsDeletedIsFalse(imageId, productId).orElseThrow(NotFoundException::new);
            productMedia.setDeleted(true);
            productMediaRepository.save(productMedia);
            deleteProductImageIfExists(productMedia.getMediaKey());
        }
    }

    @Transactional
    public void deleteProductImages(Long productId, ProductDto productDto) {
        log.info("Product's all images delete has started. productId: {}", productId);
        productMediaRepository.removeAllProductsMediaByProductId(productId);
        log.info("Product's all images delete has ended. productId: {}", productId);
        for (ProductMediaDto productMedia : productDto.getProductMedias()) {
            deleteProductImageIfExists(productMedia.getMediaKey());
        }
    }

    private void deleteProductImageIfExists(UUID currImageKey) {
        if (currImageKey != null) {
            log.info("Product image deletion has been started. currImageKey: {}", currImageKey);
            String bucketPath = MediaHelper.generateBucketPath(MediaHelper.PRODUCT_BUCKET_FOLDER, currImageKey);
            s3Service.deleteFile(bucketPath);
            log.info("Product image has been deleted. currImageKey: {}", currImageKey);
        }
    }
}
