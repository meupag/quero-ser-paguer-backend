package com.ordersapi.orders.Infrasctructure.Models;

import com.ordersapi.orders.Domain.Entities.Produto;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "produto", schema = "produtos")
public class ProductModel extends Produto {

    public ProductModel(String nome, double precoSugerido) {
        super(nome, precoSugerido);
    }

    public ProductModel() {
        super();
    }

    @Override
    @javax.persistence.Id
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
    @Column(name = "nome", nullable = false)
    public String getNome() {
        return super.getNome();
    }

    @Override
    public void setNome(String nome) {
        super.setNome(nome);
    }

    @Override
    @Column(name = "preco_sugerido", nullable = false)
    public double getPrecoSugerido() {
        return super.getPrecoSugerido();
    }

    @Override
    public void setPrecoSugerido(double precoSugerido) {
        super.setPrecoSugerido(precoSugerido);
    }
}
