package com.customer.service.customer.validator.impl;

import br.com.caelum.stella.validation.CPFValidator;
import br.com.caelum.stella.validation.InvalidStateException;
import com.customer.service.customer.exception.InvalidCustomerDocumentException;
import com.customer.service.customer.validator.DocumentValidator;
import org.springframework.stereotype.Component;

@Component
public class DocumentValidatorImpl implements DocumentValidator {

    @Override
    public void validate(String document) {
        try {
            CPFValidator cpfValidator = new CPFValidator();
            cpfValidator.assertValid(document);
        } catch (InvalidStateException e) {
            throw new InvalidCustomerDocumentException(document);
        }
    }
}