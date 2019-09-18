package com.ordersapi.orders.Application.Controllers;

import com.ordersapi.orders.Application.ViewModel.OrdersItemViewModel;
import com.ordersapi.orders.Application.ViewModel.OrdersViewModel;
import com.ordersapi.orders.Domain.Entities.Pedido;
import com.ordersapi.orders.Domain.Entities.PedidoItem;
import com.ordersapi.orders.Domain.Services.OrderDomainService;
import com.ordersapi.orders.Infrasctructure.Models.OrdersItems;
import com.ordersapi.orders.Infrasctructure.Models.OrdersModel;
import com.ordersapi.orders.Infrasctructure.Repository.OrdersItemRepository;
import com.ordersapi.orders.Infrasctructure.Repository.OrdersRepository;
import com.ordersapi.orders.Infrasctructure.Repository.ProductRepository;
import com.ordersapi.orders.Services.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/")
public class OrdersController {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private OrdersItemRepository ordersItemRepository;
    @Autowired
    private ProductRepository productRepository;

    private OrderDomainService orderDomainService;


    @PostConstruct
    public void init() {
        ClienteService clienteService = new ClienteService();
        orderDomainService = new OrderDomainService(ordersRepository, clienteService, ordersItemRepository, productRepository);
    }

    @GetMapping("/orders")
    public List<Pedido> getAllOrders() {
        return orderDomainService.listOrders();
    }

    @GetMapping("/orders/cliente/{ClienteId}")
    public List<Pedido> getAllOrders(@PathVariable(value = "ClienteId") UUID ClienteId) {
        return orderDomainService.getOrdersByClienteId(ClienteId);
    }

    @GetMapping("/orders/{id}")
    public Pedido getORdersById(@PathVariable(value = "id") UUID id) {
        return orderDomainService.getById(id);
    }

    @PostMapping("/orders")
    public Pedido createOrders(@Valid @RequestBody OrdersViewModel order) throws Exception {
        OrdersModel pedido = new OrdersModel(order.clienteId, 0, null);
        try{
            return orderDomainService.createOrder(pedido);
        }catch (Exception err){
            throw err;
        }
    };

    @PostMapping("/orders/item/{pedidoId}")
    public PedidoItem addOrderItem(@PathVariable(value = "pedidoId") UUID id, @Valid @RequestBody OrdersItemViewModel ordersItem){
        OrdersItems ordersItems = new OrdersItems(id,ordersItem.produtoId, ordersItem.quantidade);

        return (PedidoItem) orderDomainService.addOrderItem(ordersItems);
    };
    @DeleteMapping("/orders/item/remove/{orderItemId}")
    public Map<String, Boolean> removeOrderItem(@PathVariable(value = "orderItemId") UUID id) throws Exception {
        orderDomainService.removeOrderItem(id);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deleted", Boolean.TRUE);
        return response;
    };
}
