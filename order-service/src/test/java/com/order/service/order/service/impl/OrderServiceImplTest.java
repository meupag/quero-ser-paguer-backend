package com.order.service.order.service.impl;

import com.order.service.customer.service.CustomerService;
import com.order.service.order.document.Order;
import com.order.service.order.exception.OrderNotFoundException;
import com.order.service.order.exception.OrderPreConditionException;
import com.order.service.order.repository.OrderRepository;
import com.order.service.order.service.OrderService;
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
@DisplayName("Customer Service test")
public class OrderServiceImplTest {

    @Mock
    private CustomerService customerService;

    @Mock
    private OrderRepository orderRepository;

    private OrderService orderService;

    @BeforeEach
    public void before() {
        this.orderService = new OrderServiceImpl(this.orderRepository, this.customerService);
    }

    @Test
    @DisplayName("Should test save order")
    public void shouldTestSave() {
        when(this.orderRepository.save(any()))
                .thenReturn(Order.builder()
                        .id("1")
                        .build());

        Order order = Order.builder()
                .customerId("5ce16d6de0cb44053231aa1e")
                .value(BigDecimal.ONE)
                .build();
        this.orderService.save(order);

        ArgumentCaptor<Order> argument = ArgumentCaptor.forClass(Order.class);
        verify(this.orderRepository, atLeastOnce()).save(argument.capture());
    }

    @Test
    @DisplayName("Should throw OrderNotFoundException")
    public void shouldThrowOrderNotFoundException() {
        when(this.orderRepository.findAll())
                .thenReturn(Lists.newArrayList());

        assertThrows(OrderNotFoundException.class, () -> this.orderService.findAll());
    }

    @Test
    @DisplayName("Should test find all orders")
    public void shouldTestFindAll() {
        when(this.orderRepository.findAll())
                .thenReturn(Lists.newArrayList(getOrder()));

        List<Order> orders = this.orderService.findAll();

        assertThat(orders.size(), is(1));
        assertThat(orders.get(0).getId(), is(getOrder().getId()));
        assertThat(orders.get(0).getCustomerId(), is(getOrder().getCustomerId()));
        assertThat(orders.get(0).getValue(), is(getOrder().getValue()));
    }

    @Test
    @DisplayName("Should throw OrderNotFoundException in find by id")
    public void shouldThrowOrderNotFoundExceptionInFindById() {
        when(this.orderRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(OrderNotFoundException.class, () -> this.orderService.findById("5ce16d6de0cb44053231aa1e"));
    }

    @Test
    @DisplayName("Should test find by id")
    public void shouldTestFindById() {
        when(this.orderRepository.findById(anyString()))
                .thenReturn(Optional.of(getOrder()));

        Order order = this.orderService.findById("5ce16d6de0cb44053231aa1e");
        assertThat(order.getId(), is(getOrder().getId()));
        assertThat(order.getCustomerId(), is(getOrder().getCustomerId()));
        assertThat(order.getValue(), is(getOrder().getValue()));
    }

    @Test
    @DisplayName("Should throw OrderPreConditionException in update")
    public void shouldThrowOrderPreConditionExceptionInUpdate(){
        when(this.orderRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(OrderPreConditionException.class, () -> this.orderService.updateById("5ce16d6de0cb44053231aa1e", getOrder()));

        verify(this.orderRepository, never()).save(any());
    }

    @Test
    @DisplayName("Should test update order")
    public void shouldTestUpdate(){
        when(this.orderRepository.findById(anyString()))
                .thenReturn(Optional.of(getOrder()));

        this.orderService.updateById("5ce16d6de0cb44053231aa1e", getOrder());

        ArgumentCaptor<Order> argument = ArgumentCaptor.forClass(Order.class);
        verify(this.orderRepository, atLeastOnce()).save(argument.capture());
    }

    @Test
    @DisplayName("Should Throw OrderPreConditionException in delete")
    public void shouldThrowOrderPreConditionExceptionInDelete(){
        when(this.orderRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(OrderPreConditionException.class, () -> this.orderService.deleteById("5ce16d6de0cb44053231aa1e"));

        verify(this.orderRepository, never()).delete(any());
    }

    @Test
    @DisplayName("Should test delete by id")
    public void shouldTestDeleteById(){
        when(this.orderRepository.findById(anyString()))
                .thenReturn(Optional.of(getOrder()));

        this.orderService.deleteById("5ce16d6de0cb44053231aa1e");

        verify(this.orderRepository, atLeastOnce()).delete(any());
    }

    @Test
    @DisplayName("Should throw OrderPreConditionException in valid")
    public void shouldThrowOrderPreConditionExceptionInValid(){
        when(this.orderRepository.findById(anyString()))
                .thenReturn(Optional.empty());

        assertThrows(OrderPreConditionException.class, () -> this.orderService.valid("5ce16d6de0cb44053231aa1e"));
    }

    @Test
    @DisplayName("Should test valid")
    public void shouldTestValid(){
        when(this.orderRepository.findById(anyString()))
                .thenReturn(Optional.of(getOrder()));

        this.orderService.valid("5ce16d6de0cb44053231aa1e");
    }

    private Order getOrder(){
        return Order.builder()
                .id("5ce16d6de0cb44053231aa1e")
                .customerId("5ce16d6de0cb44053231aa14")
                .value(BigDecimal.TEN)
                .build();
    }
}
