package com.yayla.secondhand.secondhandbackend.service.product;


import com.yayla.secondhand.secondhandbackend.convertor.product.ProductConvertor;
import com.yayla.secondhand.secondhandbackend.exception.NotFoundException;
import com.yayla.secondhand.secondhandbackend.model.dto.product.ProductDto;
import com.yayla.secondhand.secondhandbackend.model.entity.product.Product;
import com.yayla.secondhand.secondhandbackend.model.vo.product.ProductCreateVo;
import com.yayla.secondhand.secondhandbackend.model.vo.product.ProductUpdateVo;
import com.yayla.secondhand.secondhandbackend.repository.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductConvertor productConvertor;

    public ProductDto fetchProduct(Long productId) {
        log.info("Product fetch has started. productId: {}", productId);
        Product product = productRepository.findByProductIdAndIsDeletedIsFalse(productId).orElseThrow(NotFoundException::new);
        log.info("Product fetch has ended. productId: {}", product.getProductId());
        return productConvertor.convert(product);
    }

    public void createProduct(ProductCreateVo productCreateVo) {
        log.info("Product creation has started. productCreateVo: {}", productCreateVo.toString());
        Product product = productConvertor.convert(productCreateVo);
        Product saved = productRepository.save(product);
        log.info("Product creation has ended. saved.getProductId: {}", saved.getProductId());
    }

    public ProductDto updateProduct(ProductUpdateVo productUpdateVo) {
        log.info("Product update has started. productUpdateVo: {}", productUpdateVo.toString());
        Product product = productRepository.findByProductIdAndIsDeletedIsFalse(productUpdateVo.getProductId()).orElseThrow(NotFoundException::new);
        updateValues(product, productUpdateVo);
        Product saved = productRepository.save(product);
        log.info("Product update has ended. saved.getProductId: {}", saved.getProductId());
        return productConvertor.convert(saved);
    }

    public void deleteProduct(Long productId) {
        log.info("Product delete has started. productId: {}", productId);
        Product product = productRepository.findByProductIdAndIsDeletedIsFalse(productId).orElseThrow(NotFoundException::new);
        product.setDeleted(true);
        productRepository.save(product);
        log.info("Product delete has ended. productId: {}", productId);
    }

    private void updateValues(Product product, ProductUpdateVo productUpdateVo) {
        Optional.of(productUpdateVo).map(ProductUpdateVo::getTitle).ifPresent(product::setTitle);
        Optional.of(productUpdateVo).map(ProductUpdateVo::getDescription).ifPresent(product::setDescription);
        Optional.of(productUpdateVo).map(ProductUpdateVo::isSold).ifPresent(product::setSold);
        Optional.of(productUpdateVo).map(ProductUpdateVo::getProductType).ifPresent(product::setProductType);
        Optional.of(productUpdateVo).map(ProductUpdateVo::getPrice).ifPresent(product::setPrice);
    }
}
