package com.br.meupag.ordersapi.domain;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.br.meupag.ordersapi.domain.abs.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Table(name = "produto")
public class Product extends Domain {
	
	@Column(name = "nome", columnDefinition = "VARCHAR(100)", nullable = false)
	@NotNull
	@Size(min = 3, max = 100)
	private String name;
	
	@Column(name = "preco_sugerido", columnDefinition = "DECIMAL(10,2)", nullable = false)
	@NotNull
	@Positive
	private Double suggestedPrice;
	
}
