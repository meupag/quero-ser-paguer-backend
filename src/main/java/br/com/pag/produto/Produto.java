package br.com.pag.produto;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.pag.util.MoneySerializer;
import lombok.Data;

@Data
@Entity
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@SequenceGenerator(name="produto_id_seq", sequenceName="produto_id_seq", allocationSize = 1)
public class Produto {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "produto_id_seq")
	private Long id;
		
	@NotBlank
	private String nome;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal precoSugerido;
	
	
}
