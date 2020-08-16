package br.com.pag.service.order.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@NoArgsConstructor
@Table(name="item_pedido")
@Entity
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

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="id_pedido", insertable=false, updatable=false)
    private Pedido pedido;

    public void modifyPropertiesTo(ItemPedido itemPedido) {
        setQuantidade(itemPedido.getQuantidade());
        setPreco(itemPedido.getPreco());
    }
}
