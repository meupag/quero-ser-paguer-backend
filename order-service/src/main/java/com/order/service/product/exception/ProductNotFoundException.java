package com.order.service.product.exception;

import com.order.service.exception.NotFoundException;

public class ProductNotFoundException extends NotFoundException {

    public ProductNotFoundException(String id) {
        super("Product not found " + id);
    }

    public ProductNotFoundException() {
        super("Products not found");
    }
}
