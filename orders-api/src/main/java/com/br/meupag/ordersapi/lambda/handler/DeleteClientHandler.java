package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.db.service.ClientService;
import com.br.meupag.ordersapi.domain.Client;
import com.br.meupag.ordersapi.lambda.handler.abs.AbstractDeleteHandler;

public class DeleteClientHandler extends AbstractDeleteHandler<Client> {
	
	public DeleteClientHandler() {
		this.service = new ClientService();
		this.logger = Logger.getLogger(DeleteClientHandler.class);
	}
	
	protected Client createHolderEntityFromRequest(Map<String, Object> request) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, String> parameters = (Map<String, String>) request.get("pathParameters");
		
		Client client = new Client();
		client.setId(Long.parseLong(parameters.get("id")));
		
		return client;
	}

}