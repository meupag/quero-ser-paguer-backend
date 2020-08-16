package br.com.pag.service.order.model;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode(of = "id")
@ToString(of = "id")
@NoArgsConstructor
@Table(name="pedido")
@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="id_cliente")
    private Long idCliente;

    @Column(name="valor")
    private BigDecimal valor;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="id", nullable=false, insertable = false, updatable = false)
    private Cliente cliente;

    @OneToMany(mappedBy="pedido", fetch= FetchType.LAZY, cascade = CascadeType.REMOVE)
    private List<ItemPedido> itensPedido;

    public void afterItemsSet() {
        this.valor = this.itensPedido.stream()
            .map(ip -> ip.getPreco().multiply(ip.getQuantidade()))
            .reduce(BigDecimal.ZERO, (p, q) -> p.add(q));
    }
}
