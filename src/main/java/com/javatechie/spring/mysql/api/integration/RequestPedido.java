package com.javatechie.spring.mysql.api.integration;

import java.math.BigDecimal;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RequestPedido {
	
	@NotNull(message = "CPF do cliente não pode ser nulo")
	@Size(min=11,max=11, message = "CPF do cliente deve conter 11 dígitos")
	private String cpf;
	
	@NotNull(message = "Valor do pedido não pode ser nulo")
	@Digits(integer=10, fraction=2, message = "Valor do pedido deve estar no padrão DECIMAL(10,2)")
	private BigDecimal valor;
	
	@NotNull(message = "Pedido deve possuir uma lista de itens")
	private List<@Valid RequestProduto> produtos;

	
	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public BigDecimal getValor() {
		return valor;
	}

	public void setValor(BigDecimal valor) {
		this.valor = valor;
	}

	public List<RequestProduto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<RequestProduto> produtos) {
		this.produtos = produtos;
	}
	
	
}
