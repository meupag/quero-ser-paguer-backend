package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.db.service.ClientService;
import com.br.meupag.ordersapi.domain.Client;
import com.br.meupag.ordersapi.lambda.handler.abs.AbstractCreateNewHandler;
import com.br.meupag.ordersapi.lambda.handler.exception.InvalidRequestException;

public class CreateNewClientHandler extends AbstractCreateNewHandler<Client> {
	
	public CreateNewClientHandler() {
		this.service = new ClientService();
		this.logger = Logger.getLogger(CreateNewClientHandler.class);
	}
	
	protected Client createHolderEntityFromRequest(Map<String, Object> request) throws Exception {
		String requestBody = (String) request.get("body");
		
		if(requestBody == null) {
			throw new InvalidRequestException();
		}
		
		return objectMapper.readValue(requestBody, Client.class);
	}

}
