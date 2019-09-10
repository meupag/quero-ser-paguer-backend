package br.com.pag.cliente;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Data;

@Data
@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@SequenceGenerator(name="cliente_id_seq", sequenceName="cliente_id_seq", allocationSize = 1)
public class Cliente {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cliente_id_seq")
	private Long id;
		
	@NotBlank
	private String nome;
	
	@Size(min = 11, max = 11, message = "CPF deve conter exatamente 11 digitos, e apenas números.")
	private String cpf;
	
	@NotNull
	private LocalDate dataNascimento;
	
}
