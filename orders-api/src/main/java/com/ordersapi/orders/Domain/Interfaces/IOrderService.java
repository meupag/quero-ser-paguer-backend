package com.ordersapi.orders.Domain.Interfaces;

import com.ordersapi.orders.Domain.Entities.Pedido;

import java.util.List;
import java.util.UUID;

public interface IOrderService {
    Boolean CreateOrder(Pedido pedido);
    Boolean DeleteOrder(UUID OrderId);
    Pedido  GetOrderById(UUID Id);
    List<Pedido> GetOrders(int Limit, int Offset);
}
