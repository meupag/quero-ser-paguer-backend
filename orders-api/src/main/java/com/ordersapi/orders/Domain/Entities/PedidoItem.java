package com.ordersapi.orders.Domain.Entities;

import java.util.UUID;

public class PedidoItem {
    protected UUID Id;
    protected UUID PedidoId;
    protected UUID ProdutoId;
    protected int Quantidade;
    protected double Preco;


    public PedidoItem(UUID pedidoId, UUID produtoId, int quantidade) {
        PedidoId = pedidoId;
        ProdutoId = produtoId;
        Quantidade = quantidade;
    }
    public PedidoItem(){

    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }
    public UUID getPedidoId() {
        return PedidoId;
    }

    public void setPedidoId(UUID pedidoId) {
        PedidoId = pedidoId;
    }

    public UUID getProdutoId() {
        return ProdutoId;
    }

    public void setProdutoId(UUID produtoId) {
        ProdutoId = produtoId;
    }

    public int getQuantidade() {
        return Quantidade;
    }

    public void setQuantidade(int quantidade) {
        Quantidade = quantidade;
    }

    public double getPreco() {
        return Preco;
    }

    public void setPreco(double preco) {
        Preco = preco;
    }

}
