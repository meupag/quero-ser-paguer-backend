package com.ordersapi.orders.Domain.Repositories;

import com.ordersapi.orders.Domain.Entities.Pedido;

import java.util.List;
import java.util.UUID;

public interface IOrderRepository<T, ID> extends IBaseRepository<T, ID> {
    List<T> findByClienteId(UUID ClienteId);
}
