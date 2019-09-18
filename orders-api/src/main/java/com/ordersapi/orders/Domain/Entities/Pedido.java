package com.ordersapi.orders.Domain.Entities;

import java.util.List;
import java.util.UUID;

public class Pedido {
    protected UUID Id;
    protected UUID ClienteId;
    protected Double Valor;

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public Double getValor() {
        return Valor;
    }

    public void setValor(Double valor) {
        Valor = valor;
    }

    public List<PedidoItem> getPedidoItens() {
        return PedidoItens;
    }

    public void setPedidoItens(List<PedidoItem> pedidoItens) {
        PedidoItens = pedidoItens;
    }

    public UUID getClienteId() {
        return this.ClienteId;
    }

    public void setClienteId(UUID clienteId) {
        this.ClienteId = clienteId;
    }

    protected List<PedidoItem> PedidoItens;

    public Pedido(UUID clientId, double valor, List<PedidoItem> pedidoItems){
        this.ClienteId = clientId;
        this.Valor = valor;
        this.PedidoItens = pedidoItems;
    }

    public Pedido(){
    }

}
