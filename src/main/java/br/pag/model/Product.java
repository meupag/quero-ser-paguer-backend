package br.pag.model;

import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Data;

/**
 *
 * @author brunner.klueger
 */
@Data
@Entity
@Table(name = "PRODUTO")
public class Product extends AbstractEntity {

    @Column(name = "NOME")
    @NotEmpty(message = "O campo [nome] do Produto é obrigatório")
    private String name;

    @Column(name = "PRECO_SUGERIDO")
    @NotNull(message = "O campo [preço sugerido] do Produto é obrigatório")
    private Double suggestedPrice;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "product")
    private Set<ItemOrder> itemOrders;

    public Product() {
    }

}
