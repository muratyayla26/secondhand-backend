package com.yayla.secondhand.secondhandbackend.manager;

import com.yayla.secondhand.secondhandbackend.convertor.product.ProductRequestToVoConvertor;
import com.yayla.secondhand.secondhandbackend.convertor.product.ProductUpdateRequestToVoConvertor;
import com.yayla.secondhand.secondhandbackend.model.dto.ProductDto;
import com.yayla.secondhand.secondhandbackend.model.request.ProductCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.request.ProductUpdateRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import com.yayla.secondhand.secondhandbackend.model.response.ProductResponse;
import com.yayla.secondhand.secondhandbackend.model.vo.ProductCreateVo;
import com.yayla.secondhand.secondhandbackend.model.vo.ProductUpdateVo;
import com.yayla.secondhand.secondhandbackend.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductManager {

    private final ProductService productService;
    private final ProductRequestToVoConvertor productRequestToVoConvertor;
    private final ProductUpdateRequestToVoConvertor productUpdateRequestToVoConvertor;

    public ProductResponse fetchProduct(Long productId) {
        log.info("Product fetch has started. productId : {}", productId);
        ProductDto productDto = productService.fetchProduct(productId);
        return mapResponse(productDto);
    }

    public ProductResponse createProduct(ProductCreateRequest productCreateRequest) {
        log.info("Product creation has started. productCreateRequest : {}", productCreateRequest.toString());
        ProductCreateVo productCreateVo = productRequestToVoConvertor.convert(productCreateRequest);
        ProductDto productDto = productService.createProduct(productCreateVo);
        return mapResponse(productDto);
    }

    public ProductResponse updateProduct(ProductUpdateRequest productUpdateRequest){
        log.info("Product update has started. productUpdateRequest : {}", productUpdateRequest.toString());
        ProductUpdateVo productUpdateVo = productUpdateRequestToVoConvertor.convert(productUpdateRequest);
        ProductDto productDto = productService.updateProduct(productUpdateVo);
        return mapResponse(productDto);
    }

    public BaseResponse deleteProduct(Long productId) {
        log.info("Product delete has started. productId: {}", productId);
        productService.deleteProduct(productId);
        return new BaseResponse();
    }

    private ProductResponse mapResponse(ProductDto productDto){
        ProductResponse productResponse = new ProductResponse();
        productResponse.setProductDto(productDto);
        return productResponse;
    }

}
