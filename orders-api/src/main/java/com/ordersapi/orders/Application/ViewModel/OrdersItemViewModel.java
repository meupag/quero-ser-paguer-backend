package com.ordersapi.orders.Application.ViewModel;

import com.ordersapi.orders.Domain.Entities.Pedido;
import com.ordersapi.orders.Domain.Entities.Produto;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class OrdersItemViewModel {
    @NotNull
    public UUID produtoId;

    @NotNull
    @Min(1)
    public int quantidade;
}
