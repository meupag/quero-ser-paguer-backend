package br.com.pag.queroserpaguer.domain;


import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 *  ItemPedido.
 *  
 *  @author igor.nunes
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = {"id","produto","pedido"})
@ToString(of = "id")
@Entity
@Table(name = "item_pedido")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ItemPedido implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(length = 11)
    private Long id;

    @NotNull
    @Column(name = "quantidade", precision = 10, scale = 2)
    private BigDecimal quantidade;

    @NotNull
    @Column(name = "preco", precision = 10, scale = 2)
    private BigDecimal preco;

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("itensPedidos")
    @JoinColumn(name = "id_pedido")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Pedido pedido;
    
    @NotNull
    @OneToOne(optional = false,fetch = FetchType.EAGER)
    @JoinColumn(name = "id_produto")
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Produto produto;

	
	

   
}
