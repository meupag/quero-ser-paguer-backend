package br.com.pag.service.order.exception;

import lombok.Getter;

@Getter
public class ClientNotFoundByIdConstraintException extends RuntimeException {
    private Long id;

    public ClientNotFoundByIdConstraintException(final Long id) {
        super("pag.client.byid.constraint.notfound");
        this.id = id;
    }
}
