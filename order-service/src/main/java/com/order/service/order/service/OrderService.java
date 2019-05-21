package com.order.service.order.service;

import com.order.service.order.document.Order;

import java.util.List;

public interface OrderService {

    Order save(Order order);

    List<Order> findAll();

    Order findById(String id);

    Order updateById(String id, Order order);

    void deleteById(String id);

    void valid(String id);
}
