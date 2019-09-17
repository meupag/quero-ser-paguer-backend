package com.ordersapi.orders.Domain.Repositories;

import java.util.List;

public interface IBaseRepository<T, ID> {
    List<T> findAll();
    T save(T order);
    T findAllById(ID Id);
    void deleteById(ID Id);
}
