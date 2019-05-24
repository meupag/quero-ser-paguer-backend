package br.com.pag.service.order.exception.handler;

import lombok.Data;

import java.util.Date;

@Data
public class ErrorResponse {
    private Date timestamp;
    private String status;
    private String message;
}
