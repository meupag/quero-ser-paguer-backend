package com.br.meupag.ordersapi.db.service;

import com.br.meupag.ordersapi.db.service.abs.AbstractService;
import com.br.meupag.ordersapi.domain.Client;
import com.br.meupag.ordersapi.domain.Order;
import com.br.meupag.ordersapi.domain.OrderItem;

public class ClientService extends AbstractService<Client> {
	
	protected void cleanRecursiveReferences(Client client) throws Exception {
		for(Order order : client.getOrders()) {
			order.setClient(null);
			
			for(OrderItem orderItem : order.getOrderItems()) {
				orderItem.setOrder(null);
			}
		}
	}
	
	protected void merge(Client oldClient, Client newClient) throws Exception {
		oldClient.setName(newClient.getName() != null ? newClient.getName() : oldClient.getName());
		oldClient.setCpf(newClient.getCpf() != null ? newClient.getCpf() : oldClient.getCpf());
		oldClient.setBirthDay(newClient.getBirthDay() != null ? newClient.getBirthDay() : oldClient.getBirthDay());
	}
	
	protected String getFindAllQuery() {
		return "SELECT c FROM Client c";
	}
	
}
