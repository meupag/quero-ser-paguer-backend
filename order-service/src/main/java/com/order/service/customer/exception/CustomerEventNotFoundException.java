package com.order.service.customer.exception;

public class CustomerEventNotFoundException extends RuntimeException {

    public CustomerEventNotFoundException(String id) {
        super("Customer not found " + id);
    }

}
