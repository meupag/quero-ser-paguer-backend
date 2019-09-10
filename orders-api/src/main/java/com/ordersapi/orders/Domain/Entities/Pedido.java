package com.ordersapi.orders.Domain.Entities;

import java.util.List;
import java.util.UUID;

public class Pedido {
    public UUID Id;
    public UUID ClienteId;
    public double Valor;
    public List<PedidoItem> PedidoItens;

}
