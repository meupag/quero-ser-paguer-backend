package com.order.service.orderitem.service.impl;

import com.order.service.order.service.OrderService;
import com.order.service.orderitem.document.OrderItem;
import com.order.service.orderitem.exception.OrderItemNotFoundException;
import com.order.service.orderitem.exception.OrderItemPreConditionException;
import com.order.service.orderitem.repository.OrderItemRepository;
import com.order.service.orderitem.service.OrderItemService;
import com.order.service.product.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class OrderItemServiceImpl implements OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductService productService;
    private final OrderService orderService;

    public OrderItemServiceImpl(OrderItemRepository orderItemRepository, ProductService productService,
                                OrderService orderService) {
        this.orderItemRepository = orderItemRepository;
        this.productService = productService;
        this.orderService = orderService;
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        this.productService.valid(orderItem.getProductId());
        this.orderService.valid(orderItem.getOrderId());
        return this.orderItemRepository.save(orderItem);
    }

    @Override
    public List<OrderItem> findAll() {
        List<OrderItem> orderItems = this.orderItemRepository.findAll();
        if(orderItems.isEmpty()){
            throw new OrderItemNotFoundException();
        }
        return orderItems;
    }

    @Override
    public OrderItem findById(String id) {
        Optional<OrderItem> orderItemOptional = this.orderItemRepository.findById(id);
        return orderItemOptional.orElseThrow(() -> new OrderItemNotFoundException(id));
    }

    @Override
    public OrderItem updateById(String id, OrderItem orderItem) {
        Optional<OrderItem> orderItemOptional = this.orderItemRepository.findById(id);
        if(!orderItemOptional.isPresent()){
            throw new OrderItemPreConditionException(id);
        }
        this.productService.valid(orderItem.getProductId());
        this.orderService.valid(orderItem.getOrderId());
        orderItem.setId(id);
        return this.orderItemRepository.save(orderItem);
    }

    @Override
    public void deleteById(String id) {
        Optional<OrderItem> orderItemOptional = this.orderItemRepository.findById(id);
        if(!orderItemOptional.isPresent()){
            throw new OrderItemPreConditionException(id);
        }
        this.orderItemRepository.delete(orderItemOptional.get());
    }
}
