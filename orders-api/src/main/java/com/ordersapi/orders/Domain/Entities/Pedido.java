package com.ordersapi.orders.Domain.Entities;

import java.util.List;
import java.util.UUID;

public abstract class Pedido {
    protected UUID Id;
    protected UUID ClienteId;
    protected double Valor;
    protected List<PedidoItem> PedidoItens;

    public Pedido(UUID id, UUID clientId, double valor, List<PedidoItem> pedidoItems){
        this.Id = id;
        this.ClienteId = clientId;
        this.Valor = valor;
        this.PedidoItens = pedidoItems;
    }
    public Pedido(){

    }

}
