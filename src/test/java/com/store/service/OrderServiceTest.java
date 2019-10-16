package com.store.service;

import com.store.AbstractTest;
import com.store.domain.Order;
import com.store.domain.OrderItem;
import com.store.exception.ResourceNotFoundException;
import com.store.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;

@WebMvcTest(value = OrderService.class)
public class OrderServiceTest extends AbstractTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private OrderRepository orderRepository;
    @MockBean
    private CustomerService customerService;

    private Order orderVerification;
    private Order orderTest;

    private Integer wrongOrderId;

    @Before
    public void setup() {
        super.setup();
        orderVerification = setupOrder();
        orderVerification.setValue(BigDecimal.ZERO);
        orderTest = orderVerification.toBuilder().build();
        wrongOrderId = orderId + 1;

        given(orderRepository.findByIdAndCustomer_Id(orderId, customerId)).willReturn(Optional.ofNullable(orderTest));
        given(orderRepository.existsByIdAndCustomer_Id(orderId, customerId)).willReturn(true);
    }

    @Test
    public void whenSaveOrderReturnOrder_thenAssertionSucceeds() {
        given(orderRepository.save(orderTest)).willReturn(orderTest);

        Order persisted = orderService.save(customerId, orderTest);
        verifyOrder(persisted);
    }

    @Test
    public void whenFindOrder_thenAssertionSucceeds() {
        Order persisted = orderService.findOrder(customerId, orderId);
        verifyOrder(persisted);
    }

    @Test
    public void whenFindOrderByExample_thenAssertionSucceeds() {
        List<Order> orderList = new ArrayList<>();
        orderList.add(orderTest);
        Page<Order> pageOrder = new PageImpl<>(orderList);
        given(orderRepository.findAll(Example.of(orderTest), Pageable.unpaged())).willReturn(pageOrder);

        Page<Order> persisted = orderService.findOrderByExample(customerId, orderTest, Pageable.unpaged());
        verifyOrder(persisted.getContent().get(0));
    }

    @Test
    public void whenAddOrderItemList_thenCalculateOrderValue() {
        List<OrderItem> orderItemList = new ArrayList<>();
        orderItemList.add(setupOrderItem());
        orderItemList.add(setupOrderItem().toBuilder().amount(BigDecimal.valueOf(15.00)).build());
        orderTest.setOrderItemList(orderItemList);

        Order persisted = orderService.updateValueById(customerId, orderId);

        orderVerification.setValue(BigDecimal.valueOf(24.00));
        verifyOrder(persisted);
    }

    @Test
    public void whenDeleteOrder_thenAssertionSucceeds() {
        orderService.deleteById(customerId, orderId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenFindOrderNotFound_thenResourceNotFoundException() {
        orderService.findOrder(customerId, wrongOrderId);
    }

    @Test(expected = ResourceNotFoundException.class)
    public void whenDeleteOrderNotFound_thenResourceNotFoundException() {
        orderService.deleteById(customerId, wrongOrderId);
    }

    private void verifyOrder(Order orderToVerify) {
        assertEquals(orderToVerify.getId(), orderVerification.getId());
        assertEquals(0, orderToVerify.getValue().compareTo(orderVerification.getValue()));
    }

}
