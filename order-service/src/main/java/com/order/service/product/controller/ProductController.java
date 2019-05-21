package com.order.service.product.controller;

import com.order.service.product.service.ProductService;
import com.order.service.product.document.Product;
import com.swagger.customer.service.api.ProductApi;
import com.swagger.customer.service.model.ProductRequest;
import com.swagger.customer.service.model.ProductResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class ProductController implements ProductApi {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<Void> saveProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .recommendationPrice(productRequest.getRecommendedPrice())
                .build();
        this.productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<ProductResponse>> findAllProducts() {
        List<Product> products = this.productService.findAll();
        List<ProductResponse> productsResponse = products.stream()
                .map(p -> new ProductResponse()
                        .id(p.getId())
                        .name(p.getName())
                        .recommendedPrice(p.getRecommendationPrice()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(productsResponse);
    }

    @Override
    public ResponseEntity<ProductResponse> findProductById(String id) {
        Product product = this.productService.findById(id);
        ProductResponse productResponse = new ProductResponse()
                .id(product.getId())
                .name(product.getName())
                .recommendedPrice(product.getRecommendationPrice());
        return ResponseEntity.ok(productResponse);
    }

    @Override
    public ResponseEntity<Void> updateProductById(String id, ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .recommendationPrice(productRequest.getRecommendedPrice())
                .build();
        this.productService.updateById(id, product);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteProductById(String id) {
        this.productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}