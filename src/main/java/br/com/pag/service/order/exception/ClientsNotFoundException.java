package br.com.pag.service.order.exception;

public class ClientsNotFoundException extends RuntimeException {

    public ClientsNotFoundException() {
        super("pag.client.all.notfound");
    }
}
