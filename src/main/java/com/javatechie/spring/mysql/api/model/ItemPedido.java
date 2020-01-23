package com.javatechie.spring.mysql.api.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "item_pedido")
public class ItemPedido {

	@Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(name="id_pedido")
    private Long idPedido;

    @Column(name="id_produto")
    private Long idProduto;

    @Column(name="quantidade")
    private BigDecimal quantidade;

    @Column(name="preco")
    private BigDecimal preco;
    
    @ManyToOne
    @JoinColumn(name="id_produto", insertable=false, updatable=false)
    private Produto produto;

    @ManyToOne
    @JoinColumn(name="id_pedido", insertable=false, updatable=false)
    private Pedido pedido;

    
    public ItemPedido() {
    	super();
    }
    
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getIdPedido() {
		return idPedido;
	}

	public void setIdPedido(Long idPedido) {
		this.idPedido = idPedido;
	}

	public Long getIdProduto() {
		return idProduto;
	}

	public void setIdProduto(Long idProduto) {
		this.idProduto = idProduto;
	}

	public BigDecimal getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(BigDecimal quantidade) {
		this.quantidade = quantidade;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
	}

	@Override
	public String toString() {
		return "ItemPedido [id=" + id + ", idPedido=" + idPedido + ", idProduto=" + idProduto + ", quantidade="
				+ quantidade + ", preco=" + preco + "]";
	}

	
	
	
	
}
