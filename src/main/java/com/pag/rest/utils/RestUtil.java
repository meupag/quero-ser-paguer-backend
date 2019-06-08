package com.pag.rest.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pag.exceptions.Error;
import com.pag.exceptions.RestUtilException;

public class RestUtil {

	static {
		mapper = new ObjectMapper();
		df = new SimpleDateFormat( "dd/MM/yyyy");
		}
	
	private static ObjectMapper mapper;
	private static DateFormat df;
	
	public static String toJson(Object obj) throws RestUtilException{
		mapper.setDateFormat(df);
		String jsonInString = null;
		try {
			jsonInString = mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			throw new RestUtilException(e);
		}
		
		return jsonInString;
	}
	
	public static <T> T fromJson(String obj,Class<T> classe) throws RestUtilException{
		T objeto = null;
		try {
			objeto = mapper.readValue(obj, classe);
		}  catch (Exception e) {
			throw new RestUtilException(e);
		}
		
		return objeto;
	}
	
	public static ResponseEntity<String> toResponseError(String mensagemErro, HttpStatus httpStatus) throws RestUtilException {
		String json = toJson(new Error(mensagemErro,httpStatus.value()));
		return new ResponseEntity<String>(json,httpStatus);
	}
	
	
	public static ResponseEntity<String> getResponseByList(List<?> lista) throws RestUtilException{
		
		if(hasEncontrouResultado(lista)) {
			String json = toJson(lista);
			return new ResponseEntity<String>(json,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	
	public static ResponseEntity<String> getResponseByOptional(Optional<?> optional) throws RestUtilException{
		
		if(optional.isPresent()) {
			String json = toJson(optional.get());
			return new ResponseEntity<String>(json,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	
	public static ResponseEntity<String> getResponseByEntity(Object entity) throws RestUtilException{
		
		if(entity != null) {
			String json = toJson(entity);
			return new ResponseEntity<String>(json,HttpStatus.OK);
		}else {
			return new ResponseEntity<String>(HttpStatus.NOT_FOUND);
		}
	}
	
	
	private static boolean hasEncontrouResultado(List<?> lista) {
		return lista != null && !lista.isEmpty();
	}
	
}