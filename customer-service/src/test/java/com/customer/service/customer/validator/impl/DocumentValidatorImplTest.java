package com.customer.service.customer.validator.impl;

import com.customer.service.customer.exception.InvalidCustomerDocumentException;
import com.customer.service.customer.validator.DocumentValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Document validator test")
public class DocumentValidatorImplTest {

    private DocumentValidator documentValidator;

    @BeforeEach
    public void before() {
        this.documentValidator = new DocumentValidatorImpl();
    }

    @Test
    public void shouldThrowInvalidDocumentException() {
        assertThrows(InvalidCustomerDocumentException.class, () -> this.documentValidator.validate("1"));
    }

    @Test
    public void shouldValidDocument() {
        this.documentValidator.validate("26988305068");
    }
}