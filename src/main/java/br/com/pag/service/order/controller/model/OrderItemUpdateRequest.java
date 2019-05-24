package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.ItemPedido;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OrderItemUpdateRequest {

    @NotNull(message = "pag.orderitem.required.id")
    private Long id;

    private Long idProduto;

    @NotNull(message = "pag.orderitem.required.quantity")
    private BigDecimal quantidade;

    @NotNull(message = "pag.orderitem.required.price")
    private BigDecimal preco;

    public static ItemPedido toModel(OrderItemUpdateRequest request) {
        return new ItemPedido()
            .setId(request.getId())
            .setIdProduto(request.getIdProduto())
            .setQuantidade(request.getQuantidade())
            .setPreco(request.getPreco());
    }
}
