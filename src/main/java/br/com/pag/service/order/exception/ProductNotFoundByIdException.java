package br.com.pag.service.order.exception;

import lombok.Data;

@Data
public class ProductNotFoundByIdException extends RuntimeException {
    private Long id;

    public ProductNotFoundByIdException(final Long id) {
        super("pag.product.byid.notfound");
        this.id = id;
    }
}
