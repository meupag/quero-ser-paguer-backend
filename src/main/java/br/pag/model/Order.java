package br.pag.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author brunner.klueger
 */
@Entity
@Table(name = "PEDIDO")
@Getter
@Setter
@ApiModel(description = "Todos os detalhes de um Order(Pedido)")
public class Order extends AbstractEntity {

    @Column(name = "VALOR")
    @NotNull(message = "{bean.order.value.NotNull}")
    @ApiModelProperty(notes = "Valor do Pedido")
    private Double value;

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID")
    @JsonIgnore
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "order")
    @ApiModelProperty(notes = "Items do pedido")
    private Set<ItemOrder> itemOrders = new HashSet<>();

    public Order() {
    }

}
