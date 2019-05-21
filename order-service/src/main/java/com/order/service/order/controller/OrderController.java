package com.order.service.order.controller;

import com.order.service.order.document.Order;
import com.order.service.order.service.OrderService;
import com.swagger.customer.service.api.OrderApi;
import com.swagger.customer.service.model.OrderRequest;
import com.swagger.customer.service.model.OrderResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class OrderController implements OrderApi {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<Void> saveOrder(OrderRequest orderRequest) {
        Order order = Order.builder()
                .customerId(orderRequest.getCustomerId())
                .value(orderRequest.getValue())
                .build();
        this.orderService.save(order);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<OrderResponse>> findAllOrders() {
        List<Order> orders = this.orderService.findAll();
        List<OrderResponse> ordersResponse = orders.stream()
                .map(o -> new OrderResponse()
                        .id(o.getId())
                        .customerId(o.getCustomerId())
                        .value(o.getValue()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(ordersResponse);
    }

    @Override
    public ResponseEntity<OrderResponse> findOrderById(String id) {
        Order order = this.orderService.findById(id);
        OrderResponse orderResponse = new OrderResponse()
                .id(order.getId())
                .customerId(order.getCustomerId())
                .value(order.getValue());
        return ResponseEntity.ok(orderResponse);
    }

    @Override
    public ResponseEntity<Void> updateOrderById(String id, OrderRequest orderRequest) {
        Order order = Order.builder()
                .customerId(orderRequest.getCustomerId())
                .value(orderRequest.getValue())
                .build();
        this.orderService.updateById(id, order);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteOrderById(String id) {
        this.orderService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}