package com.ordersapi.orders.Domain.Entities;

import java.util.UUID;

public class PedidoItem {
    public UUID Id;
    public UUID PedidoId;
    public UUID ProdutoId;
    public int Quantidade;
    public double Preco;

    public Pedido Pedido;
    public Produto Produto;

}
