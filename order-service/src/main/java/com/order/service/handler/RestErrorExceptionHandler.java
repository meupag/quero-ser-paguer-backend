package com.order.service.handler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.order.service.exception.NotFoundException;
import com.order.service.exception.PreConditionException;
import com.swagger.customer.service.model.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class RestErrorExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {

        List<String> errors = ex.getBindingResult().getFieldErrors()
                .stream().map(f -> String.format("%s%s%s", f.getField(), " ", f.getDefaultMessage()))
                .collect(Collectors.toList());

        ErrorMessage errorMessage = new ErrorMessage().errors(errors);
        return handleExceptionInternal(ex, errorMessage, headers, HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
                                                                  HttpHeaders headers, HttpStatus status, WebRequest request) {
        Object message = "";
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException exception = (InvalidFormatException) ex.getCause();
            message = exception.getValue();
        }

        ErrorMessage errorMessage = new ErrorMessage().addErrorsItem("Its not possible convert value " + message);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PreConditionException.class)
    public ResponseEntity<Object> handlerCustomerDocumentAlreadyExistException(PreConditionException ex) {
        ErrorMessage errorMessage = new ErrorMessage().addErrorsItem(ex.getMessage());
        log.error("Info log with param = {}", ex.getMessage(), ex);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.PRECONDITION_FAILED);
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<Object> handlerCustomerDocumentAlreadyExistException(NotFoundException ex) {
        ErrorMessage errorMessage = new ErrorMessage().addErrorsItem(ex.getMessage());
        log.error("Info log with param = {}", ex.getMessage(), ex);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handlerException(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage().addErrorsItem("Its not possible to proccess your request, try again !");
        log.error("Info log with param = {}", ex.getMessage(), ex);
        return new ResponseEntity<>(errorMessage, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}