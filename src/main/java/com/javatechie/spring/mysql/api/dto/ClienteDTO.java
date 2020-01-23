package com.javatechie.spring.mysql.api.dto;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonFormat;


public class ClienteDTO {
	
	@NotNull(message = "Nome do cliente não pode ser nulo")
	@Size(min=1, max=100, message = "Nome do cliente deve ser de 1 a 100 caracteres")
    private String nome;
	
	@NotNull(message = "CPF do cliente não pode ser nulo")
	@Size(min=11, max=11, message = "CPF deve conter 11 dígitos")
    private String cpf;

	@JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT-3")
	@NotNull(message = "Data de Nascimento do cliente não pode ser nula")
	@PastOrPresent(message = "Data de Nascimento inválida")
    private Date dataNascimento;

	
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
	
	
	
}
