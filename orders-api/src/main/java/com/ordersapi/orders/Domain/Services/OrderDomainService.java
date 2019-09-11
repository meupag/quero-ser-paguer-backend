package com.ordersapi.orders.Domain.Services;

import com.ordersapi.orders.Domain.Entities.Pedido;
import com.ordersapi.orders.Domain.Repositories.IOrderRepository;
import com.ordersapi.orders.Infrasctructure.Repository.OrdersRepository;

import java.util.List;

public class OrderDomainService {
    private IOrderRepository _repository;

    public OrderDomainService(IOrderRepository repository){
        _repository = repository;
    }

    public List<Pedido> listOrders(){
        return _repository.findAll();
    }

    public Pedido createOrder(Pedido order){
        return (Pedido) _repository.save(order);
    }

}
