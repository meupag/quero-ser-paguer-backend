package com.ordersapi.orders.Infrasctructure.Repository;

import com.ordersapi.orders.Domain.Repositories.IOrderRepository;
import com.ordersapi.orders.Infrasctructure.Models.OrdersModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface OrdersRepository extends JpaRepository<OrdersModel, UUID>, IOrderRepository<OrdersModel, UUID> {
}
