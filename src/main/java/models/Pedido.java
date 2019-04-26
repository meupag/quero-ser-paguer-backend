package main.java.models;

import java.util.Date;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name="pedido")
public class Pedido {

    @Id 
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name="id_cliente", nullable = false)
    private Cliente cliente;

    @OneToMany(mappedBy = "pedido")
    private Set<ItemPedido> itemPedidos;

    @Column(name="valor")
    private float valor;

    protected Pedido(){}

    public Pedido(Cliente cliente, float valor){
        this.cliente = cliente;
        this.valor = valor;
    }

    public int getId() {
        return id;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public float getValor() {
        return valor;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public void setValor(float valor) {
        this.valor = valor;
    }
}