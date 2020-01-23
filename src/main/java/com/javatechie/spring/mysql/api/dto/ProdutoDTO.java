package com.javatechie.spring.mysql.api.dto;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class ProdutoDTO {

	@NotNull(message = "Nome do produto não pode ser nulo")
	@Size(min=1,max=100, message = "Nome do produto deve conter no máximo 100 caracteres")
	private String nome;
	
	@NotNull(message = "Preco sugerido do produto não pode ser nulo")
	@Digits(integer=10, fraction=2, message = "Preco sugerido deve estar no padrão DECIMAL(10,2)")
	private BigDecimal precoSugerido;


	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public BigDecimal getPrecoSugerido() {
		return precoSugerido;
	}

	public void setPrecoSugerido(BigDecimal precoSugerido) {
		this.precoSugerido = precoSugerido;
	}
	
	
	
}
