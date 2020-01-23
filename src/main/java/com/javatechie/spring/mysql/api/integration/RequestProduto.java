package com.javatechie.spring.mysql.api.integration;

import java.math.BigDecimal;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class RequestProduto {

	@NotNull(message = "Id do produto não pode ser nulo")
	private Long idProduto;
	
	@NotNull(message = "Quantidade do produto não pode ser nulo")
	@Digits(integer=10, fraction=2, message = "Quantidade do produto deve estar no padrão DECIMAL(10,2)")
	private BigDecimal quantidade;

	@NotNull(message = "Preco do produto não pode ser nulo")
	@Digits(integer=10, fraction=2, message = "Preco do produto deve estar no padrão DECIMAL(10,2)")
	private BigDecimal preco;
	
	
	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	
	
}
