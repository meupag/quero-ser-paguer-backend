package com.store.controller;

import com.store.api.OrderApi;
import com.store.domain.Customer;
import com.store.domain.Order;
import com.store.exception.PostWithIdException;
import com.store.message.ExceptionMessage;
import com.store.resource.OrderResource;
import com.store.service.OrderService;
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
public class OrderController implements OrderApi {

    private OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ResponseEntity<OrderResource> addOrder(Integer customerId, Order order) {
        if (order.getId() != null) {
            throw new PostWithIdException(ExceptionMessage.PostWithId);
        }

        Order persistedOrder = orderService.save(customerId, order);
        return new ResponseEntity<>(new OrderResource(persistedOrder), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<OrderResource> getOrderById(@PathVariable Integer customerId, @PathVariable Integer orderId) {
        Order order = orderService.findOrder(customerId, orderId);
        return ResponseEntity.ok(new OrderResource(order));
    }

    @Override
    public ResponseEntity<PagedResources<Order>> getOrder(@PathVariable Integer customerId, Integer orderId, Customer customer, BigDecimal value, Pageable pageable, PagedResourcesAssembler assembler) {
        Order orderExample = Order.builder().id(orderId).customer(customer).value(value).build();
        Page<Order> orderPage = orderService.findOrderByExample(customerId, orderExample, pageable);
        return new ResponseEntity<PagedResources<Order>>(assembler.toResource(orderPage), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Void> deleteOrderById(Integer customerId, Integer orderId) {
        orderService.deleteById(customerId, orderId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
