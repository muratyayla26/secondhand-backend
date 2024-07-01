package com.yayla.secondhand.secondhandbackend.controller;

import com.yayla.secondhand.secondhandbackend.manager.ProductManager;
import com.yayla.secondhand.secondhandbackend.model.request.product.ProductCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.request.product.ProductImagesDeleteRequest;
import com.yayla.secondhand.secondhandbackend.model.request.product.ProductUpdateRequest;
import com.yayla.secondhand.secondhandbackend.model.response.BaseResponse;
import com.yayla.secondhand.secondhandbackend.model.response.ProductResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping(path = "/api/v1/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductManager productManager;

    @GetMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse fetchProduct(@PathVariable Long productId) {
        return productManager.fetchProduct(productId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public BaseResponse createProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest) {
        return productManager.createProduct(productCreateRequest);
    }

    @PutMapping("/{productId}")
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProduct(@PathVariable Long productId, @Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        return productManager.updateProduct(productId, productUpdateRequest);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BaseResponse deleteProduct(@PathVariable Long productId) {
        return productManager.deleteProduct(productId);
    }

    @PostMapping("/upload-images/{productId}")
    public ResponseEntity<BaseResponse> uploadProductImages(@PathVariable Long productId, @RequestParam("files") MultipartFile[] files) {
        BaseResponse baseResponse = productManager.uploadProductImages(productId, files);
        if (baseResponse.getStatusMessage() != null && !baseResponse.getStatusMessage().isEmpty()) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(baseResponse);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
        }
    }

    @PostMapping("/delete-images/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BaseResponse deleteProductImages(@PathVariable Long productId, @Valid @RequestBody ProductImagesDeleteRequest productImagesDeleteRequest) {
        return productManager.deleteProductImages(productId, productImagesDeleteRequest);
    }
}
