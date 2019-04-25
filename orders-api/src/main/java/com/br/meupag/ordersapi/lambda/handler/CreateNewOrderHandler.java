package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.db.service.OrderService;
import com.br.meupag.ordersapi.domain.Order;
import com.br.meupag.ordersapi.lambda.handler.abs.AbstractCreateNewHandler;
import com.br.meupag.ordersapi.lambda.handler.exception.InvalidRequestException;

public class CreateNewOrderHandler extends AbstractCreateNewHandler<Order> {
	
	public CreateNewOrderHandler() {
		this.service = new OrderService();
		this.logger = Logger.getLogger(CreateNewOrderHandler.class);
	}
	
	protected Order createHolderEntityFromRequest(Map<String, Object> request) throws Exception {
		String requestBody = (String) request.get("body");
		
		if(requestBody == null) {
			throw new InvalidRequestException();
		}
		
		return objectMapper.readValue(requestBody, Order.class);
	}

}
