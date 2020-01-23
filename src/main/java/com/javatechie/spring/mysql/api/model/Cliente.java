package com.javatechie.spring.mysql.api.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.javatechie.spring.mysql.api.dto.ClienteDTO;

import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "cliente")
public class Cliente {
	
	@Id 
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @Column(name="nome")
    private String nome;

    @Column(name="cpf")
    private String cpf;

    @Column(name="data_nascimento")
    @Temporal(TemporalType.DATE)
    private Date dataNascimento;

    
    
	public Cliente() {
		super();
	}

	public Cliente(ClienteDTO clienteDto) {
		this.nome = clienteDto.getNome();
		this.cpf = clienteDto.getCpf();
		this.dataNascimento = clienteDto.getDataNascimento();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}


	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	@Override
	public String toString() {
		return "Cliente [id=" + id + ", nome=" + nome + ", cpf=" + cpf + ", dataNascimento=" + dataNascimento + "]";
	}
	
	
	
	
}
