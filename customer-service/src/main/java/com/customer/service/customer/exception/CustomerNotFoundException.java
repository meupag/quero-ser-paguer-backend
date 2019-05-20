package com.customer.service.customer.exception;

import com.customer.service.exception.NotFoundException;

public class CustomerNotFoundException extends NotFoundException {

    public CustomerNotFoundException() {
        super("Customer not found");
    }

}
