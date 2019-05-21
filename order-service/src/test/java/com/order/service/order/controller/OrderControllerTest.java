package com.order.service.order.controller;

import com.order.service.order.document.Order;
import com.order.service.order.service.OrderService;
import com.swagger.customer.service.model.OrderRequest;
import com.swagger.customer.service.model.OrderResponse;
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
@DisplayName("Order controller test")
public class OrderControllerTest {

    @Mock
    private OrderService orderService;

    private OrderController orderController;

    @BeforeEach
    public void before(){
        this.orderController = new OrderController(this.orderService);
    }

    @Test
    @DisplayName("Should test save order")
    public void shouldTestSave(){
        when(this.orderService.save(any()))
                .thenReturn(getOrder());

        OrderRequest orderRequest = new OrderRequest()
                .customerId("5ce16d6de0cb44053231aa1e")
                .value(BigDecimal.TEN);
        ResponseEntity<Void> responseEntity = this.orderController.saveOrder(orderRequest);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    @DisplayName("Should test find all orders")
    public void shouldTestFindAll(){
        when(this.orderService.findAll())
                .thenReturn(Lists.newArrayList(getOrder()));

        ResponseEntity<List<OrderResponse>> responseEntity = this.orderController.findAllOrders();

        assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getBody().size(), is(1));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().get(0).getId(), is(getOrder().getId()));
        assertThat(responseEntity.getBody().get(0).getCustomerId(), is(getOrder().getCustomerId()));
        assertThat(responseEntity.getBody().get(0).getValue(), is(getOrder().getValue()));
    }

    @Test
    @DisplayName("Should test find orders by id")
    public void shouldTestFindById(){
        when(this.orderService.findById(anyString()))
                .thenReturn(getOrder());

        ResponseEntity<OrderResponse> responseEntity = this.orderController.findOrderById("5ce16d6de0cb44053231aa1e");

        assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getId(), is(getOrder().getId()));
        assertThat(responseEntity.getBody().getCustomerId(), is(getOrder().getCustomerId()));
        assertThat(responseEntity.getBody().getValue(), is(getOrder().getValue()));
    }

    @Test
    @DisplayName("Should test update order")
    public void shouldTestUpdateById(){
        when(this.orderService.updateById(anyString(), any()))
                .thenReturn(getOrder());

        OrderRequest orderRequest = new OrderRequest()
                .customerId("5ce16d6de0cb44053231aa1e")
                .value(BigDecimal.TEN);
        ResponseEntity<Void> responseEntity = this.orderController.updateOrderById("5ce16d6de0cb44053231aa1e", orderRequest);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    @DisplayName("Should test delete order")
    public void shouldTestDeleteById(){
        doNothing()
                .when(this.orderService).deleteById(anyString());

        ResponseEntity<Void> responseEntity = this.orderController.deleteOrderById("5ce16d6de0cb44053231aa1e");

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    private Order getOrder(){
        return Order.builder()
                .id("5ce16d6de0cb44053231aa1e")
                .customerId("5ce16d6de0cb44053231aa14")
                .value(BigDecimal.TEN)
                .build();
    }
}