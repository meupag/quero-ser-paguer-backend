package com.ordersapi.orders.Infrasctructure.Models;

import com.ordersapi.orders.Domain.Entities.Pedido;
import com.ordersapi.orders.Domain.Entities.PedidoItem;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "pedido", schema = "pedidos")
public class OrdersModel extends Pedido {
    public OrdersModel(UUID clientId, double valor, List<PedidoItem> pedidoItems) {
        super(clientId, valor, pedidoItems);
    }
    public OrdersModel(){
        super();
    }


    @Override
    @Column(name = "id_cliente", nullable = false)
    public UUID getClienteId() {
        return this.ClienteId;
    }

    @Override
    public void setClienteId(UUID clienteId) {
        this.ClienteId = clienteId;
    }
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Override
    @Column(name = "id", nullable = false)
    public UUID getId() {
        return Id;
    }

    @Override
    public void setId(UUID id) {
        this.Id = id;
    }

    @Override
    @Column(name = "valor", nullable = true)
    public Double getValor(){
        return Valor;
    }

    @Override
    public void setValor(Double valor){
        this.Valor = valor;
    }

}
