package com.order.service.orderitem.controller;

import com.order.service.orderitem.document.OrderItem;
import com.order.service.orderitem.service.OrderItemService;
import com.swagger.customer.service.api.OrderItemApi;
import com.swagger.customer.service.model.OrderItemRequest;
import com.swagger.customer.service.model.OrderItemResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class OrderItemController implements OrderItemApi {

    private final OrderItemService orderItemService;

    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Override
    public ResponseEntity<Void> saveOrderItem(OrderItemRequest orderItemRequest) {
        OrderItem orderItem = OrderItem.builder()
                .orderId(orderItemRequest.getOrderId())
                .productId(orderItemRequest.getProductId())
                .quantity(orderItemRequest.getQuantity())
                .price(orderItemRequest.getPrice())
                .build();
        this.orderItemService.save(orderItem);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @Override
    public ResponseEntity<List<OrderItemResponse>> findAllOrderItems() {
        List<OrderItem> orderItems = this.orderItemService.findAll();
        List<OrderItemResponse> orderItemsResponse = orderItems.stream()
                .map(o -> new OrderItemResponse()
                        .id(o.getId())
                        .orderId(o.getOrderId())
                        .productId(o.getProductId())
                        .quantity(o.getQuantity())
                        .price(o.getPrice()))
                .collect(Collectors.toList());
        return ResponseEntity.ok(orderItemsResponse);
    }

    @Override
    public ResponseEntity<OrderItemResponse> findOrderItemById(String id) {
        OrderItem orderItem = this.orderItemService.findById(id);
        OrderItemResponse orderItemResponse = new OrderItemResponse()
                .id(orderItem.getId())
                .orderId(orderItem.getOrderId())
                .productId(orderItem.getProductId())
                .quantity(orderItem.getQuantity())
                .price(orderItem.getPrice());
        return ResponseEntity.ok(orderItemResponse);
    }

    @Override
    public ResponseEntity<Void> updateOrderItemById(String id, OrderItemRequest orderItemRequest) {
        OrderItem orderItem = OrderItem.builder()
                .orderId(orderItemRequest.getOrderId())
                .productId(orderItemRequest.getProductId())
                .quantity(orderItemRequest.getQuantity())
                .price(orderItemRequest.getPrice())
                .build();
        this.orderItemService.updateById(id, orderItem);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> deleteOrderItemById(String id) {
        this.orderItemService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}