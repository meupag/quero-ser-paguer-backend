package com.order.service.product.service.impl;

import com.order.service.product.document.Product;
import com.order.service.product.exception.ProductNotFoundException;
import com.order.service.product.exception.ProductPreConditionException;
import com.order.service.product.repository.ProductRepository;
import com.order.service.product.service.ProductService;
import org.assertj.core.util.Lists;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("Product Service test")
public class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    private ProductService productService;

    @BeforeEach
    public void before() {
        this.productService = new ProductServiceImpl(this.productRepository);
    }

    @Test
    @DisplayName("Should test save product")
    public void shouldTestSave() {
        when(this.productRepository.save(any()))
                .thenReturn(Product.builder()
                        .id("1")
                        .build());

        Product product = Product.builder()
                .name("john")
                .recommendationPrice(BigDecimal.ONE)
                .build();
        this.productService.save(product);

        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);
        verify(this.productRepository, atLeastOnce()).save(argument.capture());
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException")
    public void shouldThrowProductNotFoundException() {
        when(this.productRepository.findAll())
                .thenReturn(Lists.newArrayList());

        assertThrows(ProductNotFoundException.class, () -> this.productService.findAll());
    }

    @Test
    @DisplayName("Should test find all products")
    public void shouldTestFindAll() {
        when(this.productRepository.findAll())
                .thenReturn(Lists.newArrayList(getProduct()));

        List<Product> products = this.productService.findAll();

        assertThat(products.size(), is(1));
        assertThat(products.get(0).getId(), is(getProduct().getId()));
        assertThat(products.get(0).getName(), is(getProduct().getName()));
        assertThat(products.get(0).getRecommendationPrice(), is(getProduct().getRecommendationPrice()));
    }

    @Test
    @DisplayName("Should throw ProductNotFoundException in find by id")
    public void shouldThrowProductNotFoundExceptionInFindById() {
        when(this.productRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> this.productService.findById("5ce16d6de0cb44053231aa1e"));
    }

    @Test
    @DisplayName("Should test find by id")
    public void shouldTestFindById() {
        when(this.productRepository.findById(anyString()))
                .thenReturn(Optional.of(getProduct()));

        Product product = this.productService.findById("5ce16d6de0cb44053231aa1e");
        assertThat(product.getId(), is(getProduct().getId()));
        assertThat(product.getName(), is(getProduct().getName()));
        assertThat(product.getRecommendationPrice(), is(getProduct().getRecommendationPrice()));
    }

    @Test
    @DisplayName("Should throw ProductPreConditionException in update")
    public void shouldThrowProductPreConditionExceptionInUpdate(){
        when(this.productRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(ProductPreConditionException.class, () -> this.productService.updateById("5ce16d6de0cb44053231aa1e", getProduct()));

        verify(this.productRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should test update product")
    public void shouldTestUpdate(){
        when(this.productRepository.findById(anyString()))
                .thenReturn(Optional.of(getProduct()));

        this.productService.updateById("5ce16d6de0cb44053231aa1e", getProduct());

        ArgumentCaptor<Product> argument = ArgumentCaptor.forClass(Product.class);
        verify(this.productRepository, atLeastOnce()).save(argument.capture());
    }

    @Test
    @DisplayName("Should Throw ProductPreConditionException in delete")
    public void shouldThrowProductPreConditionExceptionInDelete(){
        when(this.productRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(ProductPreConditionException.class, () -> this.productService.deleteById("5ce16d6de0cb44053231aa1e"));

        verify(this.productRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should test delete by id")
    public void shouldTestDeleteById(){
        when(this.productRepository.findById(anyString()))
                .thenReturn(Optional.of(getProduct()));

        this.productService.deleteById("5ce16d6de0cb44053231aa1e");

        verify(this.productRepository, atLeastOnce()).delete(any());
    }

    @Test
    @DisplayName("Should throw ProductPreConditionException in valid")
    public void shouldThrowProductPreConditionExceptionInValid(){
        when(this.productRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(ProductPreConditionException.class, () -> this.productService.valid("5ce16d6de0cb44053231aa1e"));
    }

    @Test
    @DisplayName("Should test valid")
    public void shouldTestValid(){
        when(this.productRepository.findById(anyString()))
                .thenReturn(Optional.of(getProduct()));

        this.productService.valid("5ce16d6de0cb44053231aa1e");
    }

    private Product getProduct(){
        return Product.builder()
                .id("5ce16d6de0cb44053231aa1e")
                .name("john")
                .recommendationPrice(BigDecimal.TEN)
                .build();
    }
}
