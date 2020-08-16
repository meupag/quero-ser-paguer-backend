package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.Pedido;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderCreateRequest {

    @NotNull(message = "pag.order.required.clientid")
    private Long idCliente;

    @Valid
    @NotEmpty(message = "pag.order.required.items")
    private List<OrderItemCreateRequest> itens;

    public Pedido toModel() {
        final Pedido pedido = new Pedido()
            .setIdCliente(idCliente)
            .setItensPedido(itens.stream()
                .map(OrderItemCreateRequest::toModel)
                .collect(Collectors.toList()));
        pedido.afterItemsSet();
        return pedido;
    }
}
