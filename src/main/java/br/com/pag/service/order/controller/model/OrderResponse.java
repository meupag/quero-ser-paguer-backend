package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.Pedido;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponse {

    private Long id;

    private Long idCliente;

    private BigDecimal valor;

    private List<OrderItemResponse> itens;

    public static OrderResponse fromModel(final Pedido order) {
        return new OrderResponse()
            .setId(order.getId())
            .setIdCliente(order.getIdCliente())
            .setValor(order.getValor())
            .setItens(order.getItensPedido().stream()
                .map(OrderItemResponse::fromModel)
                .collect(Collectors.toList())
            );
    }
}
