package com.order.service.orderitem.service.impl;

import com.order.service.order.service.OrderService;
import com.order.service.orderitem.document.OrderItem;
import com.order.service.orderitem.exception.OrderItemNotFoundException;
import com.order.service.orderitem.exception.OrderItemPreConditionException;
import com.order.service.orderitem.repository.OrderItemRepository;
import com.order.service.orderitem.service.OrderItemService;
import com.order.service.product.document.Product;
import com.order.service.product.exception.ProductNotFoundException;
import com.order.service.product.exception.ProductPreConditionException;
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
@DisplayName("Order item Service test")
public class OrderItemServiceImplTest {

    @Mock
    private OrderItemRepository orderItemRepository;
    @Mock
    private ProductService productService;
    @Mock
    private OrderService orderService;

    private OrderItemService orderItemService;

    @BeforeEach
    public void before() {
        this.orderItemService = new OrderItemServiceImpl(this.orderItemRepository, this.productService, this.orderService);
    }

    @Test
    @DisplayName("Should test save order item")
    public void shouldTestSave() {
        doNothing()
                .when(this.productService).valid(anyString());
        doNothing()
                .when(this.orderService).valid(anyString());
        when(this.orderItemRepository.save(any()))
                .thenReturn(OrderItem.builder()
                        .id("1")
                        .build());

        OrderItem orderItem = OrderItem.builder()
                .id("5ce16d6de0cb44053231aa1e")
                .orderId("5ce33db74263b702b5c3b8d6")
                .productId("5ce33db74263b702b5c3b8d7")
                .quantity(1)
                .price(BigDecimal.ONE)
                .build();
        this.orderItemService.save(orderItem);

        ArgumentCaptor<OrderItem> argument = ArgumentCaptor.forClass(OrderItem.class);
        verify(this.orderItemRepository, atLeastOnce()).save(argument.capture());
    }

    @Test
    @DisplayName("Should throw OrderItemNotFoundException")
    public void shouldThrowOrderItemNotFoundException() {
        when(this.orderItemRepository.findAll())
                .thenReturn(Lists.newArrayList());

        assertThrows(OrderItemNotFoundException.class, () -> this.orderItemService.findAll());
    }

    @Test
    @DisplayName("Should test find all order items")
    public void shouldTestFindAll() {
        when(this.orderItemRepository.findAll())
                .thenReturn(Lists.newArrayList(getOrderItem()));

        List<OrderItem> orderItems = this.orderItemService.findAll();

        assertThat(orderItems.size(), is(1));
        assertThat(orderItems.get(0).getId(), is(getOrderItem().getId()));
        assertThat(orderItems.get(0).getOrderId(), is(getOrderItem().getOrderId()));
        assertThat(orderItems.get(0).getProductId(), is(getOrderItem().getProductId()));
        assertThat(orderItems.get(0).getQuantity(), is(getOrderItem().getQuantity()));
        assertThat(orderItems.get(0).getPrice(), is(getOrderItem().getPrice()));
    }

    @Test
    @DisplayName("Should throw OrderItemNotFoundException in find by id")
    public void shouldThrowOrderItemNotFoundExceptionInFindById() {
        when(this.orderItemRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(OrderItemNotFoundException.class, () -> this.orderItemService.findById("5ce16d6de0cb44053231aa1e"));
    }

    @Test
    @DisplayName("Should test find by id")
    public void shouldTestFindById() {
        when(this.orderItemRepository.findById(anyString()))
                .thenReturn(Optional.of(getOrderItem()));

        OrderItem orderItem = this.orderItemService.findById("5ce16d6de0cb44053231aa1e");
        assertThat(orderItem.getId(), is(getOrderItem().getId()));
        assertThat(orderItem.getOrderId(), is(getOrderItem().getOrderId()));
        assertThat(orderItem.getProductId(), is(getOrderItem().getProductId()));
        assertThat(orderItem.getQuantity(), is(getOrderItem().getQuantity()));
        assertThat(orderItem.getPrice(), is(getOrderItem().getPrice()));
    }

    @Test
    @DisplayName("Should throw OrderItemPreConditionException in update")
    public void shouldThrowOrderItemPreConditionExceptionInUpdate(){
        when(this.orderItemRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(OrderItemPreConditionException.class, () -> this.orderItemService.updateById("5ce16d6de0cb44053231aa1e", getOrderItem()));

        verify(this.orderItemRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should test update order item")
    public void shouldTestUpdate(){
        when(this.orderItemRepository.findById(anyString()))
                .thenReturn(Optional.of(getOrderItem()));
        doNothing()
                .when(this.productService).valid(anyString());
        doNothing()
                .when(this.orderService).valid(anyString());

        this.orderItemService.updateById("5ce16d6de0cb44053231aa1e", getOrderItem());

        ArgumentCaptor<OrderItem> argument = ArgumentCaptor.forClass(OrderItem.class);
        verify(this.orderItemRepository, atLeastOnce()).save(argument.capture());
    }

    @Test
    @DisplayName("Should Throw OrderItemPreConditionException in delete")
    public void shouldThrowOrderItemPreConditionExceptionInDelete(){
        when(this.orderItemRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(OrderItemPreConditionException.class, () -> this.orderItemService.deleteById("5ce16d6de0cb44053231aa1e"));

        verify(this.orderItemRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should test delete by id")
    public void shouldTestDeleteById(){
        when(this.orderItemRepository.findById(anyString()))
                .thenReturn(Optional.of(getOrderItem()));

        this.orderItemService.deleteById("5ce16d6de0cb44053231aa1e");

        verify(this.orderItemRepository, atLeastOnce()).delete(any());
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
