package com.ordersapi.orders.Application.ViewModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class OrdersViewModel {
    @NotNull
    public UUID clienteId;
    public double valor;
}
