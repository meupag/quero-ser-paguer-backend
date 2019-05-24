package br.com.pag.service.order.controller.model;

import br.com.pag.service.order.model.Pedido;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class OrderCreateRequestTest {

    private static final Long CLIENTE_1000 = 1000L;

    private OrderCreateRequest request;

    @BeforeEach
    void setUp() {
        request = new OrderCreateRequest();
    }

    @Test
    void mustConvertRequestToModel() {
        request.setIdCliente(CLIENTE_1000)
            .setItens(createOrderItemCreateRequests());

        Pedido pedido = request.toModel();

        assertThat(pedido.getIdCliente()).isEqualTo(CLIENTE_1000);
        assertThat(pedido.getValor()).isEqualTo(new BigDecimal("484.63"));
        assertThat(pedido.getItensPedido()).hasSize(3);
    }

    private List<OrderItemCreateRequest> createOrderItemCreateRequests() {
        List<OrderItemCreateRequest> requests = new ArrayList<>();
        requests.add(
            new OrderItemCreateRequest().setPreco(new BigDecimal("17.33")).setQuantidade(new BigDecimal("3")));
        requests.add(
            new OrderItemCreateRequest().setPreco(new BigDecimal("42.89")).setQuantidade(new BigDecimal("5")));
        requests.add(
            new OrderItemCreateRequest().setPreco(new BigDecimal("31.17")).setQuantidade(new BigDecimal("7")));
        return requests;
    }

}
