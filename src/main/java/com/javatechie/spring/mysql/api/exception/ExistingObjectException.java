package com.javatechie.spring.mysql.api.exception;

public class ExistingObjectException extends RuntimeException{

	public ExistingObjectException(String exception) {
		super(exception);
	}
	
}
