package br.pag.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

/**
 *
 * @author brunner.klueger
 */
@Entity
@Table(name = "PRODUTO")
@Getter
@Setter
@ApiModel(description = "Todos os detalhes de um Product(Produto)")
public class Product extends AbstractEntity {

    @Column(name = "NOME")
    @NotEmpty(message = "{bean.product.name.NotEmpty}")
    @ApiModelProperty(notes = "Nome do Produto")
    private String name;

    @Column(name = "PRECO_SUGERIDO")
    @NotNull(message = "{bean.product.suggestedPrice.NotNull}")
    @ApiModelProperty(notes = "Preço sugerido para o Produto")
    private Double suggestedPrice;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY,
            mappedBy = "product")
    @ApiModelProperty(notes = "Lista de Items do Pedido")
    private Set<ItemOrder> itemOrders = new HashSet<>();

    public Product() {
    }
}
