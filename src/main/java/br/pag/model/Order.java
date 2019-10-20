package br.pag.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import lombok.Data;

/**
 *
 * @author brunner.klueger
 */
@Data
@Entity
@Table(name = "PEDIDO")
public class Order extends AbstractEntity {

    @Column(name = "VALOR")
    @NotNull(message = "O campo [valor] do Pedido é obrigatório")
    private Double value;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ID_CLIENTE", referencedColumnName = "ID", nullable = false)
    @JsonIgnore
    private Customer customer;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "order")
    private Set<ItemOrder> itemOrders;

    public Order() {
    }

}
