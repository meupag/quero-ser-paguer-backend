package com.store.service;

import com.store.domain.Customer;
import com.store.domain.Order;
import com.store.exception.ResourceNotFoundException;
import com.store.message.ExceptionMessage;
import com.store.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerService customerService;

    @Autowired
    public OrderService(OrderRepository orderRepository, CustomerService customerService) {
        this.orderRepository = orderRepository;
        this.customerService = customerService;
    }

    public Order save(Integer customerId, Order order) {
        order.setValue(BigDecimal.ZERO);
        Customer optionalCustomer = customerService.findCustomer(customerId);
        order.setCustomer(optionalCustomer);
        return orderRepository.save(order);
    }

    public Order findOrder(Integer customerId, Integer orderId) {
        return findOrderById(customerId, orderId);
    }

    public Page<Order> findOrderByExample(Integer customerId, Order order, Pageable pageable) {
        order.setCustomer(Customer.builder().id(customerId).build());
        return orderRepository.findAll(Example.of(order), pageable);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public Order updateValueById(Integer customerId, Integer orderId) {
        Order persisted = findOrderById(customerId, orderId);
        persisted.setValue(calculateValue(persisted));
        return persisted;
    }

    public void deleteById(Integer customerId, Integer orderId) {
        if (orderRepository.existsByIdAndCustomer_Id(orderId, customerId)) {
            orderRepository.deleteById(orderId);
        } else {
            throw new ResourceNotFoundException(ExceptionMessage.OrderNotFound);
        }
    }

    private BigDecimal calculateValue(Order order) {
        return order.getOrderItemList().stream().map(a -> a.getAmount().multiply(a.getPrice())).reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private Order findOrderById(Integer customerId, Integer orderId) throws ResourceNotFoundException {
        return orderRepository.findByIdAndCustomer_Id(orderId, customerId).orElseThrow(() -> new ResourceNotFoundException(ExceptionMessage.OrderNotFound));
    }

}
