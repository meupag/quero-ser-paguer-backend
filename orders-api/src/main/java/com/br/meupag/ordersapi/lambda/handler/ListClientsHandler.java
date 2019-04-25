package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.db.service.ClientService;
import com.br.meupag.ordersapi.domain.Client;
import com.br.meupag.ordersapi.lambda.handler.abs.AbstractListHandler;

public class ListClientsHandler extends AbstractListHandler<Client> {
	
	public ListClientsHandler() {
		this.service = new ClientService();
		this.logger = Logger.getLogger(ListClientsHandler.class);
	}
	
	protected Client createHolderEntityFromRequest(Map<String, Object> request) throws Exception {
		return null;
	}

}