package com.ordersapi.orders.Infrasctructure.Models;

import com.ordersapi.orders.Domain.Entities.Pedido;
import com.ordersapi.orders.Domain.Entities.PedidoItem;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedido", schema = "pedidos")
public class OrdersModel extends Pedido {
    public OrdersModel(UUID id, UUID clientId, double valor, List<PedidoItem> pedidoItems) {
        super(id, clientId, valor, pedidoItems);
    }
    public OrdersModel(){
        super();
    }

    @Id
    @Column(name = "id", nullable = false)
    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        this.Id = id;
    }

    @Column(name = "id_cliente", nullable = false)
    public UUID getClientId(){
        return ClienteId;
    }

    public void setClientId(UUID clientID){
        this.ClienteId = clientID;
    }

    @Column(name = "valor", nullable = false)
    public double getValor(){
        return Valor;
    }

    public void setValor(double valor){
        this.Valor = valor;
    }

}
