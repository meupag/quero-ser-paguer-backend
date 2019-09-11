package com.ordersapi.orders.Domain.Entities;

import java.util.List;
import java.util.UUID;

public class Pedido {
    protected UUID Id;
    protected UUID ClienteId;
    protected Double Valor;
    protected List<PedidoItem> PedidoItens;

    public Pedido(UUID clientId, double valor, List<PedidoItem> pedidoItems){
        this.ClienteId = clientId;
        this.Valor = valor;
        this.PedidoItens = pedidoItems;
    }
    public Pedido(){

    }

}
