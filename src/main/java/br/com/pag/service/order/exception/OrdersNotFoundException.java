package br.com.pag.service.order.exception;

public class OrdersNotFoundException extends RuntimeException {

    public OrdersNotFoundException() {
        super("pag.order.all.notfound");
    }
}
