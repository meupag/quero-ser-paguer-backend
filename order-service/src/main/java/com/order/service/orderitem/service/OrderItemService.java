package com.order.service.orderitem.service;

import com.order.service.orderitem.document.OrderItem;
import com.order.service.product.document.Product;

import java.util.List;

public interface OrderItemService {

    OrderItem save(OrderItem orderItem);

    List<OrderItem> findAll();

    OrderItem findById(String id);

    OrderItem updateById(String id, OrderItem orderItem);

    void deleteById(String id);
}
