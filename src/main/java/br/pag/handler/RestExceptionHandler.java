package br.pag.handler;

import br.pag.error.ResponseBeanValidationDetails;
import br.pag.error.ResponseCommonDetails;
import br.pag.error.ResponseException;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Classe que intercepta e manipula as exceções lançadas.
 *
 * @author brunner.klueger
 */
@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Monta corpo de mensagem de erros provenientes do ResponseException(Erro
     * personalizado).
     *
     * @param responseException exceção original capturada
     * @return resposta editada
     */
    @ExceptionHandler(ResponseException.class)
    public ResponseEntity<?> handleResponseCommontException(ResponseException responseException) {
        ResponseCommonDetails responseDetails = ResponseCommonDetails.Builder
                .newBuilder()
                .title("Erro")
                .timeStamp(new Date().getTime())
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(responseException.getMessage())
                .developerMessage(responseException.getClass().getName())
                .build();
        return new ResponseEntity<>(responseDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Monta corpo de mensagem de erros provenientes de BeanValidation.
     *
     * @param methodNotValidException exceção original capturada
     * @param headers header da requisição
     * @param status status do erro
     * @param request requisição
     * @return resposta editada
     */
    @Override
    public ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException methodNotValidException,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        //monta o map de campo de exceção por seu motivo de acontecer
        Map<String, String> mapInvalidArguments = methodNotValidException
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage));

        ResponseBeanValidationDetails responseDetails = ResponseBeanValidationDetails.Builder
                .newBuilder()
                .timeStamp(new Date().getTime())
                .title("Erro de BeanValidation")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(methodNotValidException.getMessage())
                .developerMessage(methodNotValidException.getClass().getName())
                .fieldErrorMessage(mapInvalidArguments)
                .build();
        return new ResponseEntity<>(responseDetails, HttpStatus.BAD_REQUEST);
    }

    /**
     * Monta corpo de mensagem de erros provenientes de qualquer erro que não
     * esteja explicitamente capturado em outros métodos dessa classe.
     *
     * @param ex exceção
     * @param body corpo da requisição
     * @param headers header da requisição
     * @param status status do erro
     * @param request requisição
     * @return resposta editada
     */
    @Override
    public ResponseEntity<Object> handleExceptionInternal(
            Exception ex,
            Object body,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        ResponseCommonDetails responseDetails = ResponseCommonDetails.Builder
                .newBuilder()
                .title("Erro Interno do Servidor")
                .timeStamp(new Date().getTime())
                .status(status.value())
                .detail(ex.getMessage())
                .developerMessage(ex.getClass().getName())
                .build();
        return new ResponseEntity<>(responseDetails, headers, status);
    }
}
