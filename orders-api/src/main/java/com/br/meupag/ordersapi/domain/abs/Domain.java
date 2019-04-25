package com.br.meupag.ordersapi.domain.abs;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@MappedSuperclass
public abstract class Domain {

	@Id
	@Column(columnDefinition = "INT(11)")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
}
