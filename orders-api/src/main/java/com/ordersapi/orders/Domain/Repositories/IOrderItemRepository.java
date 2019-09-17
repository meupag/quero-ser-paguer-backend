package com.ordersapi.orders.Domain.Repositories;

import java.util.List;
import java.util.UUID;

public interface IOrderItemRepository<T, ID> extends IBaseRepository<T, ID> {
    List<T> findByPedidoId(UUID PedidoId);
}
