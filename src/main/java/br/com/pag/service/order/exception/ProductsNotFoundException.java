package br.com.pag.service.order.exception;

public class ProductsNotFoundException extends RuntimeException {

    public ProductsNotFoundException() {
        super("pag.product.all.notfound");
    }
}
