package com.yayla.secondhand.secondhandbackend.controller;

import com.yayla.secondhand.secondhandbackend.manager.ProductManager;
import com.yayla.secondhand.secondhandbackend.model.request.ProductCreateRequest;
import com.yayla.secondhand.secondhandbackend.model.request.ProductUpdateRequest;
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
    public ProductResponse createProduct(@Valid @RequestBody ProductCreateRequest productCreateRequest) {
        return productManager.createProduct(productCreateRequest);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ProductResponse updateProduct(@Valid @RequestBody ProductUpdateRequest productUpdateRequest) {
        return productManager.updateProduct(productUpdateRequest);
    }

    @DeleteMapping("/{productId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public BaseResponse deleteProduct(@PathVariable Long productId) {
        return productManager.deleteProduct(productId);
    }

    @PostMapping("/upload-images/{productId}")
    public ResponseEntity<BaseResponse> uploadProductImages(@PathVariable Long productId, @RequestParam("files") MultipartFile[] files) {
        BaseResponse baseResponse = productManager.uploadProductImages(productId, files);
        if (baseResponse.getErrorMessage() != null && !baseResponse.getErrorMessage().isEmpty()) {
            return ResponseEntity.status(HttpStatus.MULTI_STATUS).body(baseResponse);
        } else {
            return ResponseEntity.status(HttpStatus.CREATED).body(baseResponse);
        }
    }
}
