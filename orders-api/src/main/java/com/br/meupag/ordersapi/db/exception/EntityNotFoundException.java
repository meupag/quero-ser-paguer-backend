package com.br.meupag.ordersapi.db.exception;

import javax.persistence.NoResultException;

@SuppressWarnings("serial")
public class EntityNotFoundException extends NoResultException {
	
	@Override
	public String getMessage() {
		return super.getMessage() != null ? super.getMessage() : 
			"com.br.meupag.ordersapi.db.exception.EntityNotFoundException: entity not found";
	}
	
}
