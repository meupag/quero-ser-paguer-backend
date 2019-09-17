package com.ordersapi.orders.Application.ViewModel;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public class ProductViewModel {
    @NotNull
    public String nome;

    @Min(1)
    @Max(10000)
    @NotNull
    public double precoSugerido;
}
