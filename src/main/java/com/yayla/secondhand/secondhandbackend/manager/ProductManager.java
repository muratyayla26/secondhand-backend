package com.yayla.secondhand.secondhandbackend.manager;

import com.yayla.secondhand.secondhandbackend.convertor.product.ProductConvertor;
import com.yayla.secondhand.secondhandbackend.convertor.product.ProductMediaConvertor;
import com.yayla.secondhand.secondhandbackend.exception.BusinessException;
import com.yayla.secondhand.secondhandbackend.model.dto.product.ProductDto;
import com.yayla.secondhand.secondhandbackend.model.request.product.ProductCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.request.product.ProductImagesDeleteRequest;
import com.yayla.secondhand.secondhandbackend.model.request.product.ProductUpdateRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import com.yayla.secondhand.secondhandbackend.model.response.ProductResponse;
import com.yayla.secondhand.secondhandbackend.model.vo.product.ProductCreateVo;
import com.yayla.secondhand.secondhandbackend.model.vo.product.ProductImageVo;
import com.yayla.secondhand.secondhandbackend.model.vo.product.ProductImagesDeleteVo;
import com.yayla.secondhand.secondhandbackend.model.vo.product.ProductUpdateVo;
import com.yayla.secondhand.secondhandbackend.service.*;
import com.yayla.secondhand.secondhandbackend.service.comment.CommentAnswerService;
import com.yayla.secondhand.secondhandbackend.service.comment.CommentService;
import com.yayla.secondhand.secondhandbackend.service.product.ProductMediaService;
import com.yayla.secondhand.secondhandbackend.service.product.ProductService;
import com.yayla.secondhand.secondhandbackend.system.utility.MediaHelper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductManager {

    private final ProductService productService;
    private final ProductConvertor productConvertor;
    private final SessionInfoService sessionInfoService;
    private final CommentService commentService;
    private final CommentAnswerService commentAnswerService;
    private final ProductMediaService productMediaService;
    private final ProductMediaConvertor productMediaConvertor;

    public ProductResponse fetchProduct(Long productId) {
        log.info("Product fetch has started. productId : {}", productId);
        ProductDto productDto = productService.fetchProduct(productId);
        return mapResponse(productDto);
    }

    public BaseResponse createProduct(ProductCreateRequest productCreateRequest) {
        log.info("Product creation has started. productCreateRequest : {}", productCreateRequest.toString());
        Long currentAccountId = sessionInfoService.currentAccountId();
        ProductCreateVo productCreateVo = productConvertor.convert(productCreateRequest, currentAccountId);
        productService.createProduct(productCreateVo);
        return new BaseResponse();
    }

    public ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest) {
        log.info("Product update has started. productUpdateRequest : {}", productUpdateRequest.toString());
        Long currentAccountId = sessionInfoService.currentAccountId();
        validateAccess(productUpdateRequest.getProductId(), currentAccountId);
        ProductUpdateVo productUpdateVo = productConvertor.convert(productUpdateRequest);
        ProductDto productDto = productService.updateProduct(productUpdateVo);
        return mapResponse(productDto);
    }

    @Transactional
    public BaseResponse deleteProduct(Long productId) {
        log.info("Product delete has started. productId: {}", productId);
        Long currentAccountId = sessionInfoService.currentAccountId();
        validateAccess(productId, currentAccountId);
        ProductDto productDto = productService.fetchProduct(productId);
        commentAnswerService.deleteProductsCommentsAnswers(productId);
        commentService.deleteProductsComments(productId);
        productMediaService.deleteProductImages(productId, productDto);
        productService.deleteProduct(productId);
        return new BaseResponse();
    }

    public BaseResponse uploadProductImages(Long productId, MultipartFile[] files) {
        if (files == null || files.length == 0) {
            throw new BusinessException("File is null or empty");
        }
        log.info("Product's image upload has started. productId : {}", productId);
        Long currentAccountId = sessionInfoService.currentAccountId();
        validateAccess(productId, currentAccountId);

        List<String> invalidImages = new ArrayList<>();
        List<ProductImageVo> productImageVos = new ArrayList<>();
        for (MultipartFile file : files) {
            if (MediaHelper.validateImageType(file, true)) {
                UUID fileKey = MediaHelper.generateImageKey();
                String bucketPath = MediaHelper.generateBucketPath(MediaHelper.PRODUCT_BUCKET_FOLDER, fileKey);
                productImageVos.add(ProductImageVo.builder()
                        .file(file)
                        .fileKey(fileKey)
                        .originalFileName(file.getOriginalFilename())
                        .bucketPath(bucketPath).build());
            } else {
                invalidImages.add(file.getOriginalFilename());
            }
        }
        List<String> unloadedImages = productMediaService.uploadProductImages(productId, productImageVos);
        invalidImages.addAll(unloadedImages);
        BaseResponse baseResponse = new BaseResponse();
        if (!invalidImages.isEmpty()) {
            baseResponse.setStatusMessage(String.join(", ", invalidImages));
        }
        return baseResponse;
    }

    public BaseResponse deleteProductImages(Long productId, ProductImagesDeleteRequest productImagesDeleteRequest) {
        log.info("Product's image delete has started. productId : {}", productId);
        Long currentAccountId = sessionInfoService.currentAccountId();
        validateAccess(productId, currentAccountId);
        ProductImagesDeleteVo productImagesDeleteVo = productMediaConvertor.convert(productImagesDeleteRequest);
        productMediaService.deleteProductImages(productId, productImagesDeleteVo);
        return new BaseResponse();
    }

    private ProductResponse mapResponse(ProductDto productDto) {
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDto(productDto);
        return productResponse;
    }

    private void validateAccess(Long productId, Long currentAccountId) {
        ProductDto productDto = productService.fetchProduct(productId);
        if (!productDto.getOwnerId().equals(currentAccountId)) {
            throw new AccessDeniedException("You do not have access to this product");
        }
    }


}
