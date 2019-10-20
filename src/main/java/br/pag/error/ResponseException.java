package br.pag.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exceção personalizada.
 *
 * @author brunner.klueger
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ResponseException extends RuntimeException {

    public ResponseException(String message) {
        super(message);
    }

}
