package com.store.integration;

import com.store.AbstractTest;
import com.store.domain.Product;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.test.annotation.DirtiesContext.ClassMode;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class ProductIntegrationTest extends AbstractTest {

    private Product productVerification;
    private Product productTest;

    @Before
    public void setup() {
        super.setup();
        productVerification = setupProduct();
        productTest = productVerification.toBuilder().id(null).build();
    }

    @Test
    public void givenNewProduct_whenPostProduct_thenCreated() throws Exception {
        final ResultActions result = postResource(productPath, productTest);
        result.andExpect(status().isCreated());
        verifyJsonProductById(result);
    }

    @Test
    public void givenNewProduct_whenPostProductWithId_thenBadRequest() throws Exception {
        final ResultActions result = postResource(customerPath, productTest.toBuilder().id(productId).build());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void givenNewProduct_whenPostProductWithoutName_thenBadRequest() throws Exception {
        final ResultActions result = postResource(customerPath, productTest.toBuilder().name(null).build());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void givenNewProduct_whenPostProductWithtNegativeSuggestedPrice_thenBadRequest() throws Exception {
        final ResultActions result = postResource(customerPath, productTest.toBuilder().suggestedPrice(BigDecimal.valueOf(-1.00)).build());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void givenProductSaved_whenGetProduct_thenAssertionSucceeds() throws Exception {
        postResource(productPath, productTest);

        final ResultActions result = getResource(productPath + "/" + productId);
        result.andExpect(status().isOk());
        verifyJsonProductById(result);
    }

    @Test
    public void givenProductSaved_whenGetProductPaged_thenAssertionSucceeds() throws Exception {
        postResource(productPath, productTest);

        final ResultActions result = getResource(productPath + "?id=" + productVerification.getId() + "&name=" + productVerification.getName());
        result.andExpect(status().isOk());
        verifyJsonProductPaged(result);
    }

    @Test
    public void givenProductSaved_whenPutProduct_thenOk() throws Exception {
        postResource(productPath, productTest);

        final ResultActions result = putResource(productPath + "/" + productId, productVerification);
        result.andExpect(status().isOk());
        verifyJsonProductById(result);
    }

    @Test
    public void givenProductSaved_whenDeleteProduct_thenNoContent() throws Exception {
        postResource(productPath, productTest);

        deleteResource(productPath, productId).andExpect(status().isNoContent()).andExpect(content().string(StringUtils.EMPTY));
    }

    private void verifyJsonProductById(final ResultActions result) throws Exception {
        verifyJsonProduct(result, "product");
        result
                .andExpect(jsonPath("_links.products.href", is(productPath)))
                .andExpect(jsonPath("_links.self.href", is(productPath + "/" + productId)));
    }

    private void verifyJsonProductPaged(final ResultActions result) throws Exception {
        verifyJsonProduct(result, "_embedded.productList[0]");
        result.andExpect(jsonPath("_links.self.href", is(productPath + "?page=0&size=10")));
    }

    private void verifyJsonProduct(final ResultActions result, String productPath) throws Exception {
        result
                .andExpect(jsonPath(productPath + ".id", is(productVerification.getId())))
                .andExpect(jsonPath(productPath + ".name", is(productVerification.getName())))
                .andExpect(jsonPath(productPath + ".suggestedPrice", is(productVerification.getSuggestedPrice().setScale(2, RoundingMode.HALF_UP).toString())))
                .andExpect(jsonPath(productPath + ".productStatus", is(productVerification.getProductStatus().toString())));
    }
}
