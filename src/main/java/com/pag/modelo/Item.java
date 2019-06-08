package com.pag.modelo;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBAttribute;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBDocument;

@DynamoDBDocument
public class Item implements Serializable {

	
	
	private static final long serialVersionUID = 1L;
	@NotBlank(message = "O identificador do produto é obrigatório")
	private String id;
	@Pattern(regexp = "^[0-9]*$",message="Quantidade só pode ter números")
	private Double quantidade;
	private Double preco;
	private String nome;
	
	@DynamoDBAttribute(attributeName = "id")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	
	@DynamoDBAttribute(attributeName = "quantidade")
	public Double getQuantidade() {
		return quantidade;
	}
	
	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}
	
	@DynamoDBAttribute(attributeName = "preco")
	public Double getPreco() {
		return preco;
	}
	
	public void setPreco(Double preco) {
		this.preco = preco;
	}
	
	public String getNome() {
		return nome;
	}
	
	@DynamoDBAttribute(attributeName = "nome")
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	
}
