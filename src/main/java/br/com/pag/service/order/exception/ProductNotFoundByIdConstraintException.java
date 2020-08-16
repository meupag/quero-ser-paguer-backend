package br.com.pag.service.order.exception;

import lombok.Data;

@Data
public class ProductNotFoundByIdConstraintException extends RuntimeException {
    private Long id;

    public ProductNotFoundByIdConstraintException(final Long id) {
        super("pag.product.byid.constraint.notfound");
        this.id = id;
    }
}
