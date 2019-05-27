package com.orders.model;

import java.util.Date;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@AllArgsConstructor // Automaticamente cria um construtor com todas as propriedades
@NoArgsConstructor // Automaticamente cria um construtor vazio
@Data // Cria toStrings, equals, hashCode, getters e setters
@EqualsAndHashCode
@Entity
public class Cliente {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Size(max = 100, message = "O nome não pode ultrapassar 100 digitos!")
  @NotBlank(message = "O nome não pode estar vazio!")
  private String nome;

  @NotBlank(message = "O número de CPF é obrigatório!")
  @Pattern(regexp = "[0-9]{11}", message = "O cpf deve conter 11 digitos!")
  @Column(unique = true)
  @Size(max = 11)
  private String cpf;

  @Temporal(TemporalType.DATE)
  @ApiModelProperty(required = false, example = "01/01/2001")
  @JsonFormat(pattern = "dd/MM/yyyy")
  @NotNull(message = "A data de nascimento é obrigatória!")
  private Date dataNascimento;

  @JsonManagedReference
  @OneToMany(targetEntity = Pedido.class, mappedBy = "cliente", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  @EqualsAndHashCode.Exclude private Set<Pedido> pedidos;

}