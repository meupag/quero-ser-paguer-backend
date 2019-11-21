package com.store.service;

import com.store.AbstractTest;
import com.store.domain.OrderItem;
import com.store.exception.ResourceNotFoundException;
import com.store.repository.OrderItemRepository;
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

@WebMvcTest(value = OrderItemService.class)
public class OrderItemServiceTest extends AbstractTest {

    @Autowired
    private OrderItemService orderItemService;

    @MockBean
    private OrderItemRepository orderItemRepository;
    @MockBean
    private OrderService orderService;
    @MockBean
    private ProductService productService;
    @MockBean
    private PropertiesCopier copier;

    private OrderItem orderItemVerification;
    private OrderItem orderItemTest;

    private Integer wrongOrderItemId;

    @Before
    public void setup() {
        super.setup();
        orderItemVerification = setupOrderItem();
        orderItemTest = setupOrderItem().toBuilder().build();
        wrongOrderItemId = orderItemId + 1;

        given(orderItemRepository.findByIdAndOrder_IdAndOrder_Customer_Id(orderItemId, orderId, customerId)).willReturn(Optional.ofNullable(orderItemTest));
        given(orderItemRepository.existsByIdAndOrder_IdAndOrder_Customer_Id(orderItemId, orderId, customerId)).willReturn(true);
    }

    @Test
    public void whenSaveOrderItemReturnOrderItem_thenAssertionSucceeds() {
        given(orderItemRepository.save(orderItemTest)).willReturn(orderItemTest);

        OrderItem persisted = orderItemService.save(customerId, orderId, orderItemTest);
        verifyOrderItem(persisted);
    }

    @Test
    public void whenFindOrderItem_thenAssertionSucceeds() {
        OrderItem persisted = orderItemService.findOrderItem(customerId, orderId, orderItemId);
        verifyOrderItem(persisted);
    }

    @Test
    public void whenFindOrderItemByExample_thenAssertionSucceeds() {
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(orderItemTest);
        Page<OrderItem> pageOrder = new PageImpl<>(orderItemList);
        given(orderItemRepository.findAll(Example.of(orderItemTest), Pageable.unpaged())).willReturn(pageOrder);

        Page<OrderItem> persisted = orderItemService.findOrderItemByExample(customerId, orderId, orderItemTest, Pageable.unpaged());
        verifyOrderItem(persisted.getContent().get(0));
    }

    @Test
    public void whenUpdateOrderItem_thenAssertionSucceeds() {
        OrderItem persisted = orderItemService.updateById(customerId, orderId, orderItemId, orderItemTest);
        verifyOrderItem(persisted);
    }

    @Test
    public void whenDeleteOrderItem_thenAssertionSucceeds() {
        orderItemService.deleteById(customerId, orderId, orderItemId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindOrderNotFound_thenResourceNotFoundException() {
        orderItemService.findOrderItem(customerId, orderId, wrongOrderItemId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenUpdateOrderNotFound_thenResourceNotFoundException() {
        orderItemService.updateById(customerId, orderId, wrongOrderItemId, orderItemTest);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenDeleteOrderNotFound_thenResourceNotFoundException() {
        orderItemService.deleteById(customerId, orderId, wrongOrderItemId);
    }

    private void verifyOrderItem(OrderItem orderItemToVerify) {
        assertEquals(orderItemToVerify.getId(), orderItemVerification.getId());
        assertEquals(0, orderItemToVerify.getAmount().compareTo(orderItemVerification.getAmount()));
        assertEquals(0, orderItemToVerify.getPrice().compareTo(orderItemVerification.getPrice()));
    }

}