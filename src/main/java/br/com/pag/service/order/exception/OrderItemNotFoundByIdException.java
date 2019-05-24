package br.com.pag.service.order.exception;

import lombok.Getter;

@Getter
public class OrderItemNotFoundByIdException extends RuntimeException {
    private Long id;
    private Long orderId;

    public OrderItemNotFoundByIdException(final Long id, Long orderId) {
        super("pag.orderitem.byid.notfound");
        this.id = id;
        this.orderId = orderId;
    }
}
