package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.Pedido;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.hateoas.ResourceSupport;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderResponse extends ResourceSupport {

    @JsonProperty("id")
    private Long idOrder;

    private Long idCliente;

    private BigDecimal valor;

    private List<OrderItemResponse> itens;

    public static OrderResponse fromModel(final Pedido order) {
        return new OrderResponse()
            .setIdOrder(order.getId())
            .setIdCliente(order.getIdCliente())
            .setValor(order.getValor())
            .setItens(order.getItensPedido().stream()
                .map(OrderItemResponse::fromModel)
                .collect(Collectors.toList())
            );
    }
}
