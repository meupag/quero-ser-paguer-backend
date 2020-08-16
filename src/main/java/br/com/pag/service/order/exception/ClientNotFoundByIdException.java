package br.com.pag.service.order.exception;

import lombok.Getter;

@Getter
public class ClientNotFoundByIdException extends RuntimeException {
    private Long id;

    public ClientNotFoundByIdException(final Long id) {
        super("pag.client.byid.notfound");
        this.id = id;
    }
}
