package com.pag.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.fasterxml.jackson.annotation.JsonFormat;


@DynamoDBTable(tableName = "cliente")
public class Cliente implements Serializable{


	private static final long serialVersionUID = 1L;
	@Size(max = 11, min = 11, message = "O CPF tem q ter 11 número")
	@Pattern(regexp = "^[0-9]*$",message="CPF só pode ter números")
	private String cpf;
	@Size(max = 100, message = "O nome não pode ser superior a 100 caracteres")
	private String nome;
	private Date nascidoEm;
	
	public Cliente(String nome, String cpf, Date nascidoEm) {

	}

	public Cliente() {
	
	}
	
	@DynamoDBHashKey(attributeName = "cpf")
	public String getCpf() {
		return cpf;
	}
	
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}
	
	public String getNome() {
		return nome;
	}
	
	@DynamoDBAttribute(attributeName = "nome")
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	@DynamoDBAttribute(attributeName = "data_nascimento")
	@JsonFormat(pattern = "dd/MM/yyyy")
	@DateTimeFormat(iso = DateTimeFormat.ISO.TIME)
	public Date getNascidoEm() {
		return nascidoEm;
	}
	
	public void setNascidoEm(Date nascidoEm) {
		this.nascidoEm = nascidoEm;
	}
	

}
