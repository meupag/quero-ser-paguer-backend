package com.store.integration;

import com.store.AbstractTest;
import com.store.domain.OrderItem;
import org.apache.commons.lang3.StringUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.math.RoundingMode;

import static org.hamcrest.core.Is.is;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
public class OrderItemIntegrationTest extends AbstractTest {

    private OrderItem orderItemVerification;
    private OrderItem orderItemTest;

    @Before
    public void setup() {
        super.setup();
        orderItemVerification = setupOrderItem();
        orderItemTest = orderItemVerification.toBuilder().id(null).build();

        try {
            postResource(customerPath, setupCustomer().toBuilder().id(null).build());
            postResource(orderPath, setupOrder().toBuilder().id(null).build());
            postResource(productPath, setupProduct().toBuilder().id(null).build());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void givenNewOrderItem_whenPostOrderItem_thenCreated() throws Exception {
        final ResultActions result = postResource(orderItemPath, orderItemTest);
        result.andExpect(status().isCreated());
        verifyJsonOrderById(result);
    }

    @Test
    public void givenNewOrderItem_whenPostOrderItemWithId_thenBadRequest() throws Exception {
        final ResultActions result = postResource(customerPath, orderItemTest.toBuilder().id(orderItemId).build());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void givenNewOrderItem_whenPostOrderItemWithoutAmount_thenBadRequest() throws Exception {
        final ResultActions result = postResource(customerPath, orderItemTest.toBuilder().amount(null).build());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void givenNewOrderItem_whenPostOrderItemWithtNegativeAmount_thenBadRequest() throws Exception {
        final ResultActions result = postResource(customerPath, orderItemTest.toBuilder().amount(BigDecimal.valueOf(-1.00)).build());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void givenNewOrderItem_whenPostOrderItemWithoutPrice_thenBadRequest() throws Exception {
        final ResultActions result = postResource(customerPath, orderItemTest.toBuilder().price(null).build());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void givenNewOrderItem_whenPostOrderItemWithtNegativePrice_thenBadRequest() throws Exception {
        final ResultActions result = postResource(customerPath, orderItemVerification.toBuilder().amount(BigDecimal.valueOf(-1.00)).build());
        result.andExpect(status().isBadRequest());
    }

    @Test
    public void givenOrderItemSaved_whenGetOrderItem_thenAssertionSucceeds() throws Exception {
        postResource(orderItemPath, orderItemTest);

        final ResultActions result = getResource(orderItemPath + "/" + orderItemId);
        result.andExpect(status().isOk());
        verifyJsonOrderById(result);
    }

    @Test
    public void givenOrderItemSaved_whenGetOrderItemPaged_thenAssertionSucceeds() throws Exception {
        postResource(orderItemPath, orderItemTest);

        final ResultActions result = getResource(orderItemPath + "?id=" + orderItemId);
        result.andExpect(status().isOk());
        verifyJsonOrderPaged(result);
    }

    @Test
    public void givenOrderItemSaved_whenPutOrderItem_thenOk() throws Exception {
        postResource(orderItemPath, orderItemTest);

        final ResultActions result = putResource(orderItemPath + "/" + orderItemId, orderItemVerification);
        result.andExpect(status().isOk());
        verifyJsonOrderById(result);
    }

    @Test
    public void givenOrderItemSaved_whenDeleteOrderItem_thenNoContent() throws Exception {
        postResource(orderItemPath, orderItemTest);

        deleteResource(orderItemPath, orderItemId).andExpect(status().isNoContent()).andExpect(content().string(StringUtils.EMPTY));
    }

    private void verifyJsonOrderById(final ResultActions result) throws Exception {
        verifyJsonOrder(result, "orderItem");
        result.andExpect(jsonPath("_links.self.href", is(orderItemPath + "/" + orderItemId)));
    }

    private void verifyJsonOrderPaged(final ResultActions result) throws Exception {
        verifyJsonOrder(result, "_embedded.orderItemList[0]");
        result.andExpect(jsonPath("_links.self.href", is(orderItemPath + "?id=" + orderItemId + "&page=0&size=10")));
    }

    private void verifyJsonOrder(final ResultActions result, String orderItemPath) throws Exception {
        result
                .andExpect(jsonPath(orderItemPath + ".id", is(orderItemVerification.getId())))
                .andExpect(jsonPath(orderItemPath + ".price", is(orderItemVerification.getPrice().setScale(2, RoundingMode.HALF_UP).toString())))
                .andExpect(jsonPath(orderItemPath + ".amount", is(orderItemVerification.getAmount().setScale(2, RoundingMode.HALF_UP).toString())));
    }
}
