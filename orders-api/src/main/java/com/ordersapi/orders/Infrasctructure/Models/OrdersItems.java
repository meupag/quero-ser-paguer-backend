package com.ordersapi.orders.Infrasctructure.Models;

import com.ordersapi.orders.Domain.Entities.Pedido;
import com.ordersapi.orders.Domain.Entities.PedidoItem;
import com.ordersapi.orders.Domain.Entities.Produto;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;
@Entity
@Table(name = "item_pedido", schema = "pedidos")
public class OrdersItems extends PedidoItem {

    public OrdersItems( UUID pedidoId, UUID produtoId, int quantidade) {
        super( pedidoId, produtoId, quantidade);
    }
    public  OrdersItems(){
        super();
    }
    @Override
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", nullable = false)
    public UUID getId() {
        return super.getId();
    }

    @Override
    public void setId(UUID id) {
        super.setId(id);
    }

    @Override
    @Column(name = "id_pedido", nullable = false)
    public UUID getPedidoId() {
        return super.getPedidoId();
    }

    @Override
    public void setPedidoId(UUID pedidoId) {
        super.setPedidoId(pedidoId);
    }

    @Override
    @Column(name = "id_produto", nullable = false)
    public UUID getProdutoId() {
        return super.getProdutoId();
    }

    @Override
    public void setProdutoId(UUID produtoId) {
        super.setProdutoId(produtoId);
    }

    @Override
    @Column(name = "quantidade", nullable = false)
    public int getQuantidade() {
        return super.getQuantidade();
    }

    @Override
    public void setQuantidade(int quantidade) {
        super.setQuantidade(quantidade);
    }

    @Override
    @Column(name = "preco", nullable = false)
    public double getPreco() {
        return super.getPreco();
    }

    @Override
    public void setPreco(double preco) {
        super.setPreco(preco);
    }

}
