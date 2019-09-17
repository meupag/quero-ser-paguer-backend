package com.ordersapi.orders.Infrasctructure.Repository;

import com.ordersapi.orders.Domain.Repositories.IOrderRepository;
import com.ordersapi.orders.Domain.Repositories.IProductRepository;
import com.ordersapi.orders.Infrasctructure.Models.ProductModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ProductRepository extends JpaRepository<ProductModel, UUID>, IProductRepository<ProductModel, UUID> {
}
