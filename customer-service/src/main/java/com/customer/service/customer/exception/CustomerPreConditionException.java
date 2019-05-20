package com.customer.service.customer.exception;

import com.customer.service.exception.PreConditionException;

public class CustomerPreConditionException extends PreConditionException {

    public CustomerPreConditionException() {
        super("Customer not found");
    }

}
