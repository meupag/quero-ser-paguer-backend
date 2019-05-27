package com.orders.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor // Automaticamente cria um construtor com todas as propriedades
@NoArgsConstructor // Automaticamente cria um construtor vazio
@Data // Cria toStrings, equals, hashCode, getters e setters
@Entity
public class ItemPedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @NotNull()
  private Long idPedido;

  @NotNull()
  private Long idProduto;

  @DecimalMin("0.0")
  @Column(precision = 10, scale = 2)
  @NotNull(message = "A quantidade não pode estar vazia!")
  private BigDecimal quantidade;

  @DecimalMin("0.0")
  @Column(precision = 10, scale = 2)
  @NotNull(message = "O preço não pode estar vazio!")
  private BigDecimal preco;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "idPedido", referencedColumnName = "id", insertable = false, updatable = false)
  private Pedido pedido;

}