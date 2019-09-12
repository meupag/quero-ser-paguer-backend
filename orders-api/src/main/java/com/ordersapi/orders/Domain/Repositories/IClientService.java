package com.ordersapi.orders.Domain.Repositories;

import com.ordersapi.orders.Domain.Entities.Cliente;

import java.util.UUID;

public interface IClientService {
    Cliente getClientById(UUID id) throws Exception;
}
