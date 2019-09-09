package br.com.pag.pedido;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import br.com.pag.produto.Produto;
import br.com.pag.util.MoneySerializer;
import lombok.Data;
import lombok.ToString;

@Data
@Entity
@Table(name = "item_pedido")
@JsonNaming(PropertyNamingStrategy.SnakeCaseStrategy.class)
@SequenceGenerator(name="item_pedido_id_seq", sequenceName="item_pedido_id_seq", allocationSize = 1)
public class ItemPedido {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "item_pedido_id_seq")
	private Long id;
		

	@ManyToOne
	@JoinColumn(name = "id_pedido")
	@ToString.Exclude
	@JsonIgnore
	private Pedido pedido;
	
	@ManyToOne()
	@JoinColumn(name = "id_produto")
	private Produto produto;
	
	private Integer quantidade;
	
	@JsonSerialize(using = MoneySerializer.class)
	private BigDecimal preco;
	
}