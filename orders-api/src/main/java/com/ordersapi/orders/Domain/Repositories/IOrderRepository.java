package com.ordersapi.orders.Domain.Repositories;

import com.ordersapi.orders.Domain.Entities.Pedido;

import java.util.List;

public interface IOrderRepository<T> {
    List<T> findAll();
    T save(T order);
}
