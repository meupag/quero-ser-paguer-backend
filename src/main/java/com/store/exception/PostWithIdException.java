package com.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class PostWithIdException extends RuntimeException {

    public PostWithIdException() {
        super();
    }

    public PostWithIdException(String message, Throwable cause) {
        super(message, cause);
    }

    public PostWithIdException(String message) {
        super(message);
    }

    public PostWithIdException(Throwable cause) {
        super(cause);
    }

}