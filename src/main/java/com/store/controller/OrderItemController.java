package com.store.controller;

import com.store.api.OrderItemApi;
import com.store.domain.OrderItem;
import com.store.exception.PostWithIdException;
import com.store.message.ExceptionMessage;
import com.store.resource.OrderItemResource;
import com.store.service.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedResources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
public class OrderItemController implements OrderItemApi {

    private OrderItemService orderItemService;

    @Autowired
    public OrderItemController(OrderItemService orderItemService) {
        this.orderItemService = orderItemService;
    }

    @Override
    public ResponseEntity<OrderItemResource> addOrderItem(Integer customerId, Integer orderId, OrderItem orderItem) {
        if (orderItem.getId() != null) {
            throw new PostWithIdException(ExceptionMessage.PostWithId);
        }

        OrderItem persistedOrderItem = orderItemService.save(customerId, orderId, orderItem);
        return new ResponseEntity<>(new OrderItemResource(persistedOrderItem), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<OrderItemResource> getOrderItemById(@PathVariable Integer customerId, @PathVariable Integer orderId, @PathVariable Integer orderItemId) {
        OrderItem orderItem = orderItemService.findOrderItem(customerId, orderId, orderItemId);
        return ResponseEntity.ok(new OrderItemResource(orderItem));
    }

    @Override
    public ResponseEntity<PagedResources<OrderItem>> getOrderItem(@PathVariable Integer customerId, @PathVariable Integer orderId, Integer orderItemId, BigDecimal amount, BigDecimal price, Pageable pageable, PagedResourcesAssembler assembler) {
        OrderItem orderItemExample = OrderItem.builder().id(orderItemId).amount(amount).price(price).build();
        Page<OrderItem> orderItemPage = orderItemService.findOrderItemByExample(customerId, orderId, orderItemExample, pageable);
        return new ResponseEntity<PagedResources<OrderItem>>(assembler.toResource(orderItemPage), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<OrderItemResource> updateOrderItemById(Integer customerId, Integer orderId, Integer orderItemId, OrderItem orderItem) {
        OrderItem updatedOrderItem = orderItemService.updateById(customerId, orderId, orderItemId, orderItem);
        return ResponseEntity.ok(new OrderItemResource(updatedOrderItem));
    }

    @Override
    public ResponseEntity<Void> deleteOrderItemById(Integer customerId, Integer orderId, Integer orderItemId) {
        orderItemService.deleteById(customerId, orderId, orderItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
