package com.ordersapi.orders.Application.Controllers;

import com.ordersapi.orders.Application.ViewModel.OrdersViewModel;
import com.ordersapi.orders.Domain.Entities.Pedido;
import com.ordersapi.orders.Domain.Services.OrderDomainService;
import com.ordersapi.orders.Infrasctructure.Models.OrdersModel;
import com.ordersapi.orders.Infrasctructure.Repository.OrdersRepository;
import com.ordersapi.orders.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class OrdersController {
    @Autowired
    private OrdersRepository ordersRepository;
    private OrderDomainService orderDomainService;


    @PostConstruct
    public void init() {
        ClienteService clienteService = new ClienteService();
        orderDomainService = new OrderDomainService(ordersRepository, clienteService);
    }

    @GetMapping("/orders")
    public List<Pedido> getAllOrders() {
        return orderDomainService.listOrders();
    }

    @PostMapping("/orders")
    public Pedido createOrders(@Valid @RequestBody OrdersViewModel order) throws Exception {
        OrdersModel pedido = new OrdersModel(order.clienteId, order.valor, null);
        try{
            return orderDomainService.createOrder(pedido);
        }catch (Exception err){
            throw err;
        }
    };


}
