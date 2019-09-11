package com.ordersapi.orders.Controllers;

import com.ordersapi.orders.Infrasctructure.Models.OrdersModel;
import com.ordersapi.orders.Infrasctructure.Repository.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/")
public class OrdersController {
    @Autowired
    private OrdersRepository ordersRepository;

    @GetMapping("/orders")
    public List<OrdersModel> getAllEmployees() {
        return ordersRepository.findAll();
    }
}
