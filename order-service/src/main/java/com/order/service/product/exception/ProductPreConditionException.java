package com.order.service.product.exception;

import com.order.service.exception.PreConditionException;

public class ProductPreConditionException extends PreConditionException {

    public ProductPreConditionException(String id) {
        super("Product not found " + id);
    }
}
