package main.java.models;

import java.util.Date;
import java.util.Set;
import javax.persistence.*;

@Entity
@Table(name="produto")
public class Produto {

    @Id 
    @GeneratedValue
    private int id;

    @Column(name="nome")
    private String nome;

    @Column(name="preco_sugerido")
    private float precoSugerido;

    @OneToMany(mappedBy = "produto")
    private Set<ItemPedido> itemPedidos;

    protected Produto(){}

    public Produto(String nome, float precoSugerido){
        this.nome = nome;
        this.precoSugerido = precoSugerido;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public float getPrecoSugerido() {
        return precoSugerido;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setPrecoSugerido(float precoSugerido) {
        this.precoSugerido = precoSugerido;
    }
}