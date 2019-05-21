package com.order.service.customer.exception;

import com.order.service.exception.PreConditionException;

public class CustomerPreConditionException extends PreConditionException {

    public CustomerPreConditionException(String id) {
        super("Customer not found " + id);
    }

}
