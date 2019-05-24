package br.com.pag.service.order.exception;

import lombok.Getter;

@Getter
public class OrderNotFoundByIdException extends RuntimeException {
    private Long id;

    public OrderNotFoundByIdException(final Long id) {
        super("pag.order.byid.notfound");
        this.id = id;
    }
}
