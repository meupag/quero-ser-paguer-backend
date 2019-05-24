package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.ItemPedido;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
public class OrderItemCreateRequest {

    @NotNull(message = "pag.orderitem.required.productid")
    private Long idProduto;

    @NotNull(message = "pag.orderitem.required.quantity")
    private BigDecimal quantidade;

    @NotNull(message = "pag.orderitem.required.price")
    private BigDecimal preco;

    public static ItemPedido toModel(OrderItemCreateRequest request) {
        return new ItemPedido()
            .setIdProduto(request.getIdProduto())
            .setQuantidade(request.getQuantidade())
            .setPreco(request.getPreco());
    }
}
