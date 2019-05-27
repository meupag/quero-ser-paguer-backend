package com.orders.model;

import java.math.BigDecimal;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor // Automaticamente cria um construtor com todas as propriedades
@NoArgsConstructor // Automaticamente cria um construtor vazio
@Data // Cria toStrings, equals, hashCode, getters e setters
@EqualsAndHashCode(exclude = "itensPedidos")
@Entity
public class Pedido {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private Long idCliente;

  @DecimalMin("0.0")
  @Column(precision = 10, scale = 2)
  @NotNull(message = "O valor não pode estar vazio!")
  private BigDecimal valor;

  @JsonBackReference
  @ManyToOne
  @JoinColumn(name = "idCliente", referencedColumnName = "id", insertable = false, updatable = false)
  private Cliente cliente;

  @JsonManagedReference
  @OneToMany(targetEntity = ItemPedido.class, mappedBy = "pedido", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  private Set<ItemPedido> itensPedidos;

}