package com.br.meupag.ordersapi.lambda.handler.exception;

@SuppressWarnings("serial")
public class InvalidRequestException extends Exception {

	@Override
	public String getMessage() {
		return super.getMessage() != null ? super.getMessage() : 
			"com.br.meupag.ordersapi.lambda.handler.exception.InvalidRequestException: invalid request";
	}
	
}
