package com.order.service.order.exception;

import com.order.service.exception.PreConditionException;

public class OrderPreConditionException extends PreConditionException {

    public OrderPreConditionException(String id) {
        super("Order not found " + id);
    }
}
