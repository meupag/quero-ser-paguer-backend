package com.br.meupag.ordersapi.domain;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

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
@Table(name = "item_pedido")
public class OrderItem extends Domain {
	
	@ManyToOne
	@JoinColumn(name = "id_pedido", columnDefinition = "INT(11)")
	@NotNull
	private Order order;
	
	@ManyToOne
	@JoinColumn(name = "id_produto", columnDefinition = "INT(11)")
	@NotNull
	private Product product;
	
	@Column(name = "quantidade", columnDefinition = "DECIMAL(10,2)")
	@NotNull
	@Positive
	private Double quantity;
	
	@Column(name = "preco", columnDefinition = "DECIMAL(10,2)")
	@NotNull
	@Positive
	private Double price;
	
}
