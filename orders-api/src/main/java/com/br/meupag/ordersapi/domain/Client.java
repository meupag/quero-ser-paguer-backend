package com.br.meupag.ordersapi.domain;

import java.util.Date;
import java.util.Set;

import javax.persistence.Cacheable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.br.CPF;

import com.br.meupag.ordersapi.domain.abs.Domain;
import com.fasterxml.jackson.annotation.JsonFormat;

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
@Table(name = "cliente")
public class Client extends Domain {
	
	@Column(name = "nome", columnDefinition = "VARCHAR(100)")
	@NotNull
	@Size(min = 3, max = 100)
	private String name;
	
	@Column(columnDefinition = "CHAR(11)")
	@NotNull
	@CPF
	private String cpf;
	
	@Column(name = "data_nascimento", columnDefinition = "DATE")
	@NotNull
	@Past
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy", locale = "pt_BR")
	private Date birthDay;

	@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
	@OneToMany(mappedBy = "client", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.REMOVE)
	private Set<Order> orders;
	
}
