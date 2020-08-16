package br.com.pag.service.order.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PedidoTest {

    @Test
    void mustCalculateTotalValueFromItems() {
        Pedido pedido = createPedidoWithItems();

        pedido.afterItemsSet();

        assertThat(pedido.getValor()).isEqualTo(new BigDecimal("484.63"));
        assertThat(pedido.getItensPedido()).hasSize(3);
    }

    private Pedido createPedidoWithItems() {
        List<ItemPedido> orderItems = new ArrayList<>();
        orderItems.add(new ItemPedido().setPreco(new BigDecimal("17.33")).setQuantidade(new BigDecimal("3")));
        orderItems.add(new ItemPedido().setPreco(new BigDecimal("42.89")).setQuantidade(new BigDecimal("5")));
        orderItems.add(new ItemPedido().setPreco(new BigDecimal("31.17")).setQuantidade(new BigDecimal("7")));

        return new Pedido()
            .setItensPedido(orderItems);
    }

}
