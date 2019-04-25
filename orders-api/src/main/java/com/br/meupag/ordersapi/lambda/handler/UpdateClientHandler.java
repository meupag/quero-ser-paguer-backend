package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.db.service.ClientService;
import com.br.meupag.ordersapi.domain.Client;
import com.br.meupag.ordersapi.lambda.handler.abs.AbstractUpdateHandler;
import com.br.meupag.ordersapi.lambda.handler.exception.InvalidRequestException;

public class UpdateClientHandler extends AbstractUpdateHandler<Client> {

	public UpdateClientHandler() {
		this.service = new ClientService();
		this.logger = Logger.getLogger(UpdateClientHandler.class);
	}
	
	protected Client createHolderEntityFromRequest(Map<String, Object> request) throws Exception {
		String requestBody = (String) request.get("body");
		
		if(requestBody == null) {
			throw new InvalidRequestException();
		}
		
		@SuppressWarnings("unchecked")
		Map<String, String> parameters = (Map<String, String>) request.get("pathParameters");
		
		Client client = objectMapper.readValue(requestBody, Client.class);
		client.setId(Long.parseLong(parameters.get("id")));
		
		return client;
	}

}