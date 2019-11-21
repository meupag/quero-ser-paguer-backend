package com.store.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class CpfAlreadyUsedException extends RuntimeException {

    public CpfAlreadyUsedException() {
        super();
    }

    public CpfAlreadyUsedException(String message, Throwable cause) {
        super(message, cause);
    }

    public CpfAlreadyUsedException(String message) {
        super(message);
    }

    public CpfAlreadyUsedException(Throwable cause) {
        super(cause);
    }

}