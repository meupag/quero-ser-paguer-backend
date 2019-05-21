package com.order.service.order.exception;

import com.order.service.exception.NotFoundException;

public class OrderNotFoundException extends NotFoundException {

    public OrderNotFoundException(String id) {
        super("Order not found " + id);
    }

    public OrderNotFoundException() {
        super("Orders not found");
    }
}
