package com.customer.service.customer.exception;

import com.customer.service.exception.BadRequestException;

public class InvalidCustomerDocumentException extends BadRequestException {

    public InvalidCustomerDocumentException(String message) {
        super("Invalid document " + message);
    }
}
