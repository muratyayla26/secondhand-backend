package com.yayla.secondhand.secondhandbackend.manager;

import com.yayla.secondhand.secondhandbackend.convertor.product.ProductConvertor;
import com.yayla.secondhand.secondhandbackend.model.dto.ProductDto;
import com.yayla.secondhand.secondhandbackend.model.request.ProductCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.request.ProductUpdateRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import com.yayla.secondhand.secondhandbackend.model.response.ProductResponse;
import com.yayla.secondhand.secondhandbackend.model.vo.ProductCreateVo;
import com.yayla.secondhand.secondhandbackend.model.vo.ProductUpdateVo;
import com.yayla.secondhand.secondhandbackend.service.CommentAnswerService;
import com.yayla.secondhand.secondhandbackend.service.CommentService;
import com.yayla.secondhand.secondhandbackend.service.ProductService;
import com.yayla.secondhand.secondhandbackend.service.SessionInfoService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductManager {

    private final ProductService productService;
    private final ProductConvertor productConvertor;
    private final SessionInfoService sessionInfoService;
    private final CommentService commentService;
    private final CommentAnswerService commentAnswerService;

    public ProductResponse fetchProduct(Long productId) {
        log.info("Product fetch has started. productId : {}", productId);
        ProductDto productDto = productService.fetchProduct(productId);
        return mapResponse(productDto);
    }

    public ProductResponse createProduct(ProductCreateRequest productCreateRequest) {
        log.info("Product creation has started. productCreateRequest : {}", productCreateRequest.toString());
        Long currentAccountId = sessionInfoService.currentAccountId();
        ProductCreateVo productCreateVo = productConvertor.convert(productCreateRequest, currentAccountId);
        ProductDto productDto = productService.createProduct(productCreateVo);
        return mapResponse(productDto);
    }

    public ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest){
        log.info("Product update has started. productUpdateRequest : {}", productUpdateRequest.toString());
        Long currentAccountId = sessionInfoService.currentAccountId();
        validateAccess(productUpdateRequest.getProductId(), currentAccountId);
        ProductUpdateVo productUpdateVo = productConvertor.convert(productUpdateRequest);
        ProductDto productDto = productService.updateProduct(productUpdateVo);
        return mapResponse(productDto);
    }
    // TODO EnableTransactionManagement,clearAutomatically = true, flushAutomatically = true araştır
    // TODO product silme yoruma bağlı kaldı. Business açısından saçma duruyo. Napmak lzm?
    @Transactional
    public BaseResponse deleteProduct(Long productId) {
        log.info("Product delete has started. productId: {}", productId);
        Long currentAccountId = sessionInfoService.currentAccountId();
        validateAccess(productId, currentAccountId);
        commentAnswerService.deleteProductsCommentsAnswers(productId);
        commentService.deleteProductsComments(productId);
        productService.deleteProduct(productId);
        return new BaseResponse();
    }

    private ProductResponse mapResponse(ProductDto productDto){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDto(productDto);
        return productResponse;
    }

    private void validateAccess(Long productId, Long currentAccountId) {
        ProductDto productDto = productService.fetchProduct(productId);
        if(!productDto.getOwnerId().equals(currentAccountId)) {
            throw new AccessDeniedException("You do not have access to this product");
        }
    }

}
