package br.pag.error;

import lombok.Getter;

/**
 *
 * @author brunner.klueger
 */
@Getter
public class ErrorDetails {

    protected String title;
    protected int status;
    protected String detail;
    protected long timeStamp;
    protected String developerMessage;
}
