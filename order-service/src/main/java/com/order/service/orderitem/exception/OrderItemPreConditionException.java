package com.order.service.orderitem.exception;

import com.order.service.exception.PreConditionException;

public class OrderItemPreConditionException extends PreConditionException {

    public OrderItemPreConditionException(String id) {
        super("Order item not found " + id);
    }
}
