package com.ordersapi.orders.Infrasctructure.Repository;

import com.ordersapi.orders.Domain.Repositories.IOrderItemRepository;
import com.ordersapi.orders.Infrasctructure.Models.OrdersItems;
import com.ordersapi.orders.Infrasctructure.Models.OrdersModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface OrdersItemRepository  extends JpaRepository<OrdersItems, UUID>, IOrderItemRepository<OrdersItems, UUID> {

}
