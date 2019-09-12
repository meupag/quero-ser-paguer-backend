package com.ordersapi.orders.Domain.Repositories;

import java.util.List;

public interface IBaseRepository<T> {
    List<T> findAll();
    T save(T order);
}
