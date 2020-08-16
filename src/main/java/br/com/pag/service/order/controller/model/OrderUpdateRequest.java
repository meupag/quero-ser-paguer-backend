package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.Pedido;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;

@Data
public class OrderUpdateRequest {

    @NotNull(message = "pag.order.required.id")
    private Long id;

    private Long idCliente;

    @Valid
    @NotEmpty(message = "pag.order.required.items")
    private List<OrderItemUpdateRequest> itens;

    public Pedido toModel() {
        return new Pedido()
            .setId(id)
            .setIdCliente(idCliente)
            .setItensPedido(itens.stream()
                .map(OrderItemUpdateRequest::toModel)
                .collect(Collectors.toList()));
    }
}
