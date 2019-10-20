package br.pag.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author brunner.klueger
 */
@Data
@Entity
@Table(name = "ITEM_PEDIDO")
public class ItemOrder extends AbstractEntity {

    @Column(name = "QUANTIDADE")
    @NotNull(message = "O campo [quantidade] do Item Pedido é obrigatório")
    private Integer quantity;

    @Column(name = "PRECO")
    @NotNull(message = "O campo [preço] do Item Pedido é obrigatório")
    private Double price;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PEDIDO", referencedColumnName = "ID", nullable = false)
    @JsonIgnore
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_PRODUTO", referencedColumnName = "ID", nullable = false)
    @JsonIgnore
    private Product product;

}
