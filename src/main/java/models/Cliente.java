package main.java.models;

import java.util.Date;
import javax.persistence.*;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name="cliente")
public class Cliente {

    @Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int id;

    @Column(name="nome")
    private String nome;

    @Column(name="CPF")
    private String cpf;

    @Column(name="data_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    @OneToMany(mappedBy = "cliente")
    private Set<Pedido> pedidos;

    protected Cliente(){}

    public Cliente(String nome, String cpf, Date dataNascimento, Pedido... pedidos){
        this.nome = nome;
        this.cpf = cpf;
        this.dataNascimento = dataNascimento;

        this.pedidos = Stream.of(pedidos).collect(Collectors.toSet());
        this.pedidos.forEach(x -> x.setCliente(this));
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpf() {
        return cpf;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }
}