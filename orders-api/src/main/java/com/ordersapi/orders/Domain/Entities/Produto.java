package com.ordersapi.orders.Domain.Entities;

import java.util.UUID;

public class Produto {
    protected UUID Id;
    protected String Nome;
    protected double PrecoSugerido;

    public Produto(String nome, double precoSugerido) {
        Nome = nome;
        PrecoSugerido = precoSugerido;
    }

    public Produto() {
    }

    public UUID getId() {
        return Id;
    }

    public void setId(UUID id) {
        Id = id;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public double getPrecoSugerido() {
        return PrecoSugerido;
    }

    public void setPrecoSugerido(double precoSugerido) {
        PrecoSugerido = precoSugerido;
    }
}
