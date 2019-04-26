package main.java.models;

import java.util.Date;
import javax.persistence.*;

@Entity
@Table(name="item_pedido")
public class ItemPedido {

    @Id 
    @GeneratedValue
    private int id;

    @ManyToOne
    @JoinColumn(name="id_pedido", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name="id_produto", nullable = false)
    private Produto produto;

    @Column(name="quantidade")
    private float quantidade;

    @Column(name="preco")
    private float preco;

    protected ItemPedido(){}

    public ItemPedido(Produto produto, Pedido pedido, float quantidade, float preco){
        this.produto = produto;
        this.pedido = pedido;
        this.quantidade = quantidade;
        this.preco = preco;
    }

    public int getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public Pedido getPedido(){
        return pedido;
    }

    public float getQuantidade(){
        return quantidade;
    }

    public float getPreco() {
        return preco;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPedido(Pedido pedido){
        this.pedido = pedido;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuantidade(float quantidade) {
        this.quantidade = quantidade;
    }
}