package com.store.controller;

import com.store.api.ProductApi;
import com.store.domain.Product;
import com.store.exception.PostWithIdException;
import com.store.message.ExceptionMessage;
import com.store.resource.ProductResource;
import com.store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class ProductController implements ProductApi {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Override
    public ResponseEntity<ProductResource> addProduct(Product product) {
        if (product.getId() != null) {
            throw new PostWithIdException(ExceptionMessage.PostWithId);
        }

        Product persistedProduct = productService.save(product);
        return new ResponseEntity<>(new ProductResource(persistedProduct), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<ProductResource> getProductById(@PathVariable Integer productId) {
        Product product = productService.findProduct(productId);
        return ResponseEntity.ok(new ProductResource(product));
    }

    @Override
    public ResponseEntity<PagedResources<Product>> getProduct(Integer productId, String nome, BigDecimal suggestedPrice, Pageable pageable, PagedResourcesAssembler assembler) {
        Product productExample = Product.builder().id(productId).name(nome).suggestedPrice(suggestedPrice).build();
        Page<Product> productPage = productService.findProductByExample(productExample, pageable);
        return new ResponseEntity<PagedResources<Product>>(assembler.toResource(productPage), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ProductResource> updateProductById(Integer productId, Product product) {
        Product updatedProduct = productService.updateById(productId, product);
        return ResponseEntity.ok(new ProductResource(updatedProduct));
    }

    @Override
    public ResponseEntity<Void> deleteProductById(Integer productId) {
        productService.deleteById(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
