package com.order.service.product.controller;

import com.order.service.product.controller.ProductController;
import com.order.service.product.document.Product;
import com.order.service.product.service.ProductService;
import com.swagger.customer.service.model.ProductRequest;
import com.swagger.customer.service.model.ProductResponse;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Product controller test")
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    private ProductController productController;

    @BeforeEach
    public void before(){
        this.productController = new ProductController(this.productService);
    }

    @Test
    @DisplayName("Should test save product")
    public void shouldTestSave(){
        when(this.productService.save(any()))
                .thenReturn(getProduct());

        ProductRequest productRequest = new ProductRequest()
                .name("John")
                .recommendedPrice(BigDecimal.TEN);
        ResponseEntity<Void> responseEntity = this.productController.saveProduct(productRequest);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    @DisplayName("Should test find all products")
    public void shouldTestFindAll(){
        when(this.productService.findAll())
                .thenReturn(Lists.newArrayList(getProduct()));

        ResponseEntity<List<ProductResponse>> responseEntity = this.productController.findAllProducts();

        assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getBody().size(), is(1));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().get(0).getId(), is(getProduct().getId()));
        assertThat(responseEntity.getBody().get(0).getName(), is(getProduct().getName()));
        assertThat(responseEntity.getBody().get(0).getRecommendedPrice(), is(getProduct().getRecommendationPrice()));
    }

    @Test
    @DisplayName("Should test find product by id")
    public void shouldTestFindById(){
        when(this.productService.findById(anyString()))
                .thenReturn(getProduct());

        ResponseEntity<ProductResponse> responseEntity = this.productController.findProductById("5ce16d6de0cb44053231aa1e");

        assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getId(), is(getProduct().getId()));
        assertThat(responseEntity.getBody().getName(), is(getProduct().getName()));
        assertThat(responseEntity.getBody().getRecommendedPrice(), is(getProduct().getRecommendationPrice()));
    }

    @Test
    @DisplayName("Should test update product")
    public void shouldTestUpdateById(){
        when(this.productService.updateById(anyString(), any()))
                .thenReturn(getProduct());

        ProductRequest productRequest = new ProductRequest()
                .name("John")
                .recommendedPrice(BigDecimal.TEN);
        ResponseEntity<Void> responseEntity = this.productController.updateProductById("5ce16d6de0cb44053231aa1e", productRequest);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    @DisplayName("Should test delete product")
    public void shouldTestDeleteById(){
        doNothing()
                .when(this.productService).deleteById(anyString());

        ResponseEntity<Void> responseEntity = this.productController.deleteProductById("5ce16d6de0cb44053231aa1e");

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    private Product getProduct(){
        return Product.builder()
                .id("5ce16d6de0cb44053231aa1e")
                .name("john")
                .recommendationPrice(BigDecimal.TEN)
                .build();
    }
}