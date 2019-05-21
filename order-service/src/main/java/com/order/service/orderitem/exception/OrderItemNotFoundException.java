package com.order.service.orderitem.exception;

import com.order.service.exception.NotFoundException;

public class OrderItemNotFoundException extends NotFoundException {

    public OrderItemNotFoundException() {
        super("Order items not found");
    }

    public OrderItemNotFoundException(String id) {
        super("Order item not found " + id);
    }
}
