package com.order.service.order.service.impl;

import com.order.service.customer.service.CustomerService;
import com.order.service.order.document.Order;
import com.order.service.order.exception.OrderNotFoundException;
import com.order.service.order.exception.OrderPreConditionException;
import com.order.service.order.repository.OrderRepository;
import com.order.service.order.service.OrderService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;

    public OrderServiceImpl(OrderRepository orderRepository, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
    }

    @Override
    @CacheEvict(value = "list-order", allEntries = true)
    public Order save(Order order) {
        this.customerService.valid(order.getCustomerId());
        return this.orderRepository.save(order);
    }

    @Override
    @Cacheable(value = "list-order")
    public List<Order> findAll() {
        List<Order> orders = this.orderRepository.findAll();
        if(orders.isEmpty()){
            throw new OrderNotFoundException();
        }
        return orders;
    }

    @Override
    @Cacheable(value = "list-order")
    public Order findById(String id) {
        Optional<Order> orderOptional = this.orderRepository.findById(id);
        return orderOptional.orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Override
    @CacheEvict(value = "list-order", allEntries = true)
    public Order updateById(String id, Order order) {
        this.customerService.valid(order.getCustomerId());

        Optional<Order> orderOptional = this.orderRepository.findById(id);
        if(!orderOptional.isPresent()){
            throw new OrderPreConditionException(id);
        }
        order.setId(id);
        return this.orderRepository.save(order);
    }

    @Override
    @CacheEvict(value = "list-order", allEntries = true)
    public void deleteById(String id) {
        Optional<Order> orderOptional = this.orderRepository.findById(id);
        this.orderRepository.delete(orderOptional.orElseThrow(() -> new OrderPreConditionException(id)));
    }

    @Override
    public void valid(String id) {
        Optional<Order> orderOptional = this.orderRepository.findById(id);
        if(!orderOptional.isPresent()){
            throw new OrderPreConditionException(id);
        }
    }
}
