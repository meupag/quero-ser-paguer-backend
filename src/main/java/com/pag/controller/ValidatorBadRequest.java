package com.pag.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

public class ValidatorBadRequest {
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public Map<String, String> validarExcecoes(MethodArgumentNotValidException ex) {
		
	    Map<String, String> mapErro= new HashMap<>();
	    
	    ex.getBindingResult().getAllErrors().forEach((error) -> {
	        String campo = ((FieldError) error).getField();
	        String mensagem = error.getDefaultMessage();
	        mapErro.put(campo, mensagem);
	        mapErro.put("codigo", "400");
	    });
	    
	    return mapErro;
	}

}
