package com.store.service;

import com.store.AbstractTest;
import com.store.domain.Product;
import com.store.domain.ProductStatus;
import com.store.exception.ResourceNotFoundException;
import com.store.repository.ProductRepository;
import com.store.util.PropertiesCopier;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@WebMvcTest(value = ProductService.class)
public class ProductServiceTest extends AbstractTest {

    @Autowired
    private ProductService productService;

    @MockBean
    private ProductRepository productRepository;

    @MockBean
    private PropertiesCopier copier;

    private Product productVerification;
    private Product productTest;

    private Integer wrongProductId;

    @Before
    public void setup() {
        super.setup();
        productVerification = setupProduct();
        productTest = productVerification.toBuilder().build();
        wrongProductId = productId + 1;

        given(productRepository.findById(productId)).willReturn(Optional.ofNullable(productTest));
    }

    @Test
    public void whenSaveProductReturnActiveProduct_thenAssertionSucceeds() {
        productTest.setProductStatus(ProductStatus.INACTIVE);
        given(productRepository.save(productTest)).willReturn(productTest);

        Product persisted = productService.save(productTest);
        verifyProduct(persisted);
    }

    @Test
    public void whenFindProduct_thenAssertionSucceeds() {
        Product persisted = productService.findProduct(productId);
        verifyProduct(persisted);
    }

    @Test
    public void whenFindProductByExample_thenAssertionSucceeds() {
        List<Product> productList = new ArrayList<>();
        productList.add(productTest);
        Page<Product> pageProduct = new PageImpl<>(productList);
        given(productRepository.findAll(Example.of(productTest), Pageable.unpaged())).willReturn(pageProduct);

        Page<Product> persisted = productService.findProductByExample(productTest, Pageable.unpaged());
        verifyProduct(persisted.getContent().get(0));
    }

    @Test
    public void whenUpdateProduct_thenAssertionSucceeds() {
        Product persisted = productService.updateById(productId, productTest);
        verifyProduct(persisted);
    }

    @Test
    public void whenDeleteProduct_thenAssertionSucceeds() {
        productService.deleteById(productId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindProductNotFound_thenResourceNotFoundException() {
        productService.findProduct(wrongProductId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenUpdateProductNotFound_thenResourceNotFoundException() {
        productService.updateById(wrongProductId, productTest);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenDeleteProductNotFound_thenResourceNotFoundException() {
        productService.deleteById(wrongProductId);
    }

    private void verifyProduct(Product productToVerify) {
        assertEquals(productToVerify.getId(), productVerification.getId());
        assertEquals(0, productToVerify.getSuggestedPrice().compareTo(productVerification.getSuggestedPrice()));
        assertEquals(0, productToVerify.getProductStatus().compareTo(productVerification.getProductStatus()));
    }

}
