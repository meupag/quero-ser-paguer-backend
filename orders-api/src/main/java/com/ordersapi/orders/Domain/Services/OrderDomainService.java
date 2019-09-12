package com.ordersapi.orders.Domain.Services;

import com.ordersapi.orders.Domain.Entities.Cliente;
import com.ordersapi.orders.Domain.Entities.Pedido;
import com.ordersapi.orders.Domain.Repositories.IClientService;
import com.ordersapi.orders.Domain.Repositories.IOrderRepository;

import java.util.List;

public class OrderDomainService {
    private IOrderRepository _repository;
    private IClientService _service;
    public OrderDomainService(IOrderRepository repository, IClientService clientService)
    {
        _repository = repository;
        _service = clientService;
    }

    public List<Pedido> listOrders(){
        return _repository.findAll();
    }

    public Pedido createOrder(Pedido order) throws Exception {
        Cliente client = _service.getClientById(order.getClienteId());
        if(client != null && client.Id != null){
            return (Pedido) _repository.save(order);
        }else{
            throw new Exception("Cliente não existe na base");
        }

    }

}
