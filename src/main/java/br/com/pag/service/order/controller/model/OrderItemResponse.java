package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.ItemPedido;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;

@Data
public class OrderItemResponse extends ResourceSupport {

    @JsonProperty("id")
    private Long orderItemId;

    private Long idPedido;

    private Long idProduto;

    private BigDecimal quantidade;

    private BigDecimal preco;

    public static OrderItemResponse fromModel(ItemPedido orderItem) {
        return new OrderItemResponse()
            .setOrderItemId(orderItem.getId())
            .setIdPedido(orderItem.getIdPedido())
            .setIdProduto(orderItem.getIdProduto())
            .setQuantidade(orderItem.getQuantidade())
            .setPreco(orderItem.getPreco());
    }
}
