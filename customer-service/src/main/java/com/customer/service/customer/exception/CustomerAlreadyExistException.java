package com.customer.service.customer.exception;

import com.customer.service.exception.PreConditionException;

public class CustomerAlreadyExistException extends PreConditionException {

    public CustomerAlreadyExistException(String message) {
        super("Document already exist " + message);
    }
}
