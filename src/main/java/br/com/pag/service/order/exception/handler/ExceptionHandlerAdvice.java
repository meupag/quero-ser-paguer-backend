package br.com.pag.service.order.exception.handler;

import br.com.pag.service.order.exception.ClientNotFoundByIdConstraintException;
import br.com.pag.service.order.exception.ClientNotFoundByIdException;
import br.com.pag.service.order.exception.ClientsNotFoundException;
import br.com.pag.service.order.exception.OrderItemNotFoundByIdException;
import br.com.pag.service.order.exception.OrderNotFoundByIdException;
import br.com.pag.service.order.exception.ProductNotFoundByIdConstraintException;
import br.com.pag.service.order.exception.ProductNotFoundByIdException;
import br.com.pag.service.order.exception.ProductsNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;
import java.util.Locale;

@RestControllerAdvice
public class ExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    private static final String ZERO_RESULTS = "ZERO_RESULTS";

    @Autowired
    private MessageSource messageSource;

    private ResponseEntity<Object> createErrorResponseEntity(HttpStatus status, String message, String messageCode,
            Locale locale, Object... params) {
        return createErrorResponseEntity(status, null, message, messageCode, locale, params);
    }

    private ResponseEntity<Object> createErrorResponseEntity(HttpStatus status, String alternateStatus, String message,
            String messageCode, Locale locale, Object... params) {

        String translatedMessage = message;
        if (messageCode != null) {
            translatedMessage = messageSource.getMessage(messageCode, params, locale);
        }
        String translatedStatus = alternateStatus != null ? alternateStatus : status.name();

        ErrorResponse errorResponse = createErrorResponse(translatedStatus, translatedMessage);

        return new ResponseEntity<>(errorResponse, status);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        FieldError error = ex.getBindingResult().getFieldError();
        Locale locale = LocaleContextHolder.getLocale();
        String message = messageSource.getMessage(error.getDefaultMessage(), null, locale);

        return createErrorResponseEntity(HttpStatus.BAD_REQUEST, message, null, locale);
    }

    @Override
    protected ResponseEntity<Object> handleMissingServletRequestParameter(MissingServletRequestParameterException ex,
            HttpHeaders headers, HttpStatus status, WebRequest request) {

        Locale locale = LocaleContextHolder.getLocale();
        return createErrorResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), null, locale);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> uncaughtException(final Exception ex, Locale locale) {
        return createErrorResponseEntity(HttpStatus.BAD_REQUEST, ex.getMessage(), null, locale);
    }

    @ExceptionHandler(ClientNotFoundByIdException.class)
    public final ResponseEntity<Object> clientNotFoundById(final ClientNotFoundByIdException ex, Locale locale) {
        return createErrorResponseEntity(HttpStatus.OK, ZERO_RESULTS, null, ex.getMessage(), locale, ex.getId());
    }

    @ExceptionHandler(ClientNotFoundByIdConstraintException.class)
    public final ResponseEntity<Object> clientNotFoundByIdConstraint(final ClientNotFoundByIdConstraintException ex, Locale locale) {
        return createErrorResponseEntity(HttpStatus.CONFLICT, ZERO_RESULTS, null, ex.getMessage(), locale, ex.getId());
    }

    @ExceptionHandler(ClientsNotFoundException.class)
    public final ResponseEntity<Object> clientNotFound(final ClientsNotFoundException ex, Locale locale) {
        return createErrorResponseEntity(HttpStatus.OK, ZERO_RESULTS, null, ex.getMessage(), locale);
    }

    @ExceptionHandler(ProductNotFoundByIdException.class)
    public final ResponseEntity<Object> productNotFoundById(final ProductNotFoundByIdException ex, Locale locale) {
        return createErrorResponseEntity(HttpStatus.OK, ZERO_RESULTS, null, ex.getMessage(), locale, ex.getId());
    }

    @ExceptionHandler(ProductNotFoundByIdConstraintException.class)
    public final ResponseEntity<Object> productNotFoundByIdConstraint(final ProductNotFoundByIdConstraintException ex, Locale locale) {
        return createErrorResponseEntity(HttpStatus.CONFLICT, ZERO_RESULTS, null, ex.getMessage(), locale, ex.getId());
    }

    @ExceptionHandler(ProductsNotFoundException.class)
    public final ResponseEntity<Object> productNotFound(final ProductsNotFoundException ex, Locale locale) {
        return createErrorResponseEntity(HttpStatus.OK, ZERO_RESULTS, null, ex.getMessage(), locale);
    }

    @ExceptionHandler(OrderNotFoundByIdException.class)
    public final ResponseEntity<Object> orderNotFound(final OrderNotFoundByIdException ex, Locale locale) {
        return createErrorResponseEntity(HttpStatus.OK, ZERO_RESULTS, null, ex.getMessage(), locale, ex.getId());
    }

    @ExceptionHandler(OrderItemNotFoundByIdException.class)
    public final ResponseEntity<Object> orderItemNotFound(final OrderItemNotFoundByIdException ex, Locale locale) {
        return createErrorResponseEntity(HttpStatus.OK, ZERO_RESULTS, null, ex.getMessage(), locale, ex.getId(), ex.getOrderId());
    }

    private ErrorResponse createErrorResponse(String status, String message) {
        ErrorResponse errorResponse = new ErrorResponse();
        errorResponse.setTimestamp(new Date());
        errorResponse.setStatus(status);
        errorResponse.setMessage(message);
        return errorResponse;
    }
}
