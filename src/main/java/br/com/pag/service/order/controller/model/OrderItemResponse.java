package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.ItemPedido;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderItemResponse {

    private Long id;

    private Long idPedido;

    private Long idProduto;

    private BigDecimal quantidade;

    private BigDecimal preco;

    public static OrderItemResponse fromModel(ItemPedido orderItem) {
        return new OrderItemResponse()
            .setId(orderItem.getId())
            .setIdPedido(orderItem.getIdPedido())
            .setIdProduto(orderItem.getIdProduto())
            .setQuantidade(orderItem.getQuantidade())
            .setPreco(orderItem.getPreco());
    }
}
