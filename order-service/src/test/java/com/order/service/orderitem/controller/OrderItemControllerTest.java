package com.order.service.orderitem.controller;

import com.order.service.orderitem.document.OrderItem;
import com.order.service.orderitem.service.OrderItemService;
import com.swagger.customer.service.model.OrderItemRequest;
import com.swagger.customer.service.model.OrderItemResponse;
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
@DisplayName("Order Item controller test")
public class OrderItemControllerTest {

    @Mock
    private OrderItemService orderItemService;

    private OrderItemController orderItemController;

    @BeforeEach
    public void before(){
        this.orderItemController = new OrderItemController(this.orderItemService);
    }

    @Test
    @DisplayName("Should test save order item")
    public void shouldTestSave(){
        when(this.orderItemService.save(any()))
                .thenReturn(getOrderItem());

        OrderItemRequest orderItemRequest = new OrderItemRequest()
                .orderId("5ce33db74263b702b5c3b8d6")
                .productId("5ce33db74263b702b5c3b8d7")
                .quantity(1)
                .price(BigDecimal.ONE);
        ResponseEntity<Void> responseEntity = this.orderItemController.saveOrderItem(orderItemRequest);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
    }

    @Test
    @DisplayName("Should test find all order items")
    public void shouldTestFindAll(){
        when(this.orderItemService.findAll())
                .thenReturn(Lists.newArrayList(getOrderItem()));

        ResponseEntity<List<OrderItemResponse>> responseEntity = this.orderItemController.findAllOrderItems();

        assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getBody().size(), is(1));
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().get(0).getId(), is(getOrderItem().getId()));
        assertThat(responseEntity.getBody().get(0).getOrderId(), is(getOrderItem().getOrderId()));
        assertThat(responseEntity.getBody().get(0).getProductId(), is(getOrderItem().getProductId()));
        assertThat(responseEntity.getBody().get(0).getQuantity(), is(getOrderItem().getQuantity()));
        assertThat(responseEntity.getBody().get(0).getPrice(), is(getOrderItem().getPrice()));
    }

    @Test
    @DisplayName("Should test find order item by id")
    public void shouldTestFindById(){
        when(this.orderItemService.findById(anyString()))
                .thenReturn(getOrderItem());

        ResponseEntity<OrderItemResponse> responseEntity = this.orderItemController.findOrderItemById("5ce16d6de0cb44053231aa1e");

        assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        assertThat(responseEntity.getBody().getId(), is(getOrderItem().getId()));
        assertThat(responseEntity.getBody().getOrderId(), is(getOrderItem().getOrderId()));
        assertThat(responseEntity.getBody().getProductId(), is(getOrderItem().getProductId()));
        assertThat(responseEntity.getBody().getQuantity(), is(getOrderItem().getQuantity()));
        assertThat(responseEntity.getBody().getPrice(), is(getOrderItem().getPrice()));
    }

    @Test
    @DisplayName("Should test update order item")
    public void shouldTestUpdateById(){
        when(this.orderItemService.updateById(anyString(), any()))
                .thenReturn(getOrderItem());

        OrderItemRequest orderItemRequest = new OrderItemRequest()
                .orderId("5ce33db74263b702b5c3b8d6")
                .productId("5ce33db74263b702b5c3b8d7")
                .quantity(1)
                .price(BigDecimal.ONE);
        ResponseEntity<Void> responseEntity = this.orderItemController.updateOrderItemById("5ce16d6de0cb44053231aa1e", orderItemRequest);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    @DisplayName("Should test delete order item")
    public void shouldTestDeleteById(){
        doNothing()
                .when(this.orderItemService).deleteById(anyString());

        ResponseEntity<Void> responseEntity = this.orderItemController.deleteOrderItemById("5ce16d6de0cb44053231aa1e");

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    private OrderItem getOrderItem(){
        return OrderItem.builder()
                .id("5ce16d6de0cb44053231aa1e")
                .orderId("5ce33db74263b702b5c3b8d6")
                .productId("5ce33db74263b702b5c3b8d7")
                .quantity(1)
                .price(BigDecimal.ONE)
                .build();
    }
}