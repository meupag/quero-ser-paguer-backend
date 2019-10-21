package br.pag.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author brunner.klueger
 */
@Entity
@Table(name = "ITEM_PEDIDO")
@Getter
@Setter
@ApiModel(description = "Todos os detalhes de um ItemOrder(PedidoItem)")
public class ItemOrder extends AbstractEntity {

    @Column(name = "QUANTIDADE")
    @NotNull(message = "{bean.itemOrder.quantity.NotNull}")
    @ApiModelProperty(notes = "Quantidade")
    private Integer quantity;

    @Column(name = "PRECO")
    @NotNull(message = "{bean.itemOrder.price.NotNull}")
    @JsonIgnore
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
