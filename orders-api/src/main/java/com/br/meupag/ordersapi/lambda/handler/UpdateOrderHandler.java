package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.db.service.OrderService;
import com.br.meupag.ordersapi.domain.Order;
import com.br.meupag.ordersapi.lambda.handler.abs.AbstractUpdateHandler;
import com.br.meupag.ordersapi.lambda.handler.exception.InvalidRequestException;

public class UpdateOrderHandler extends AbstractUpdateHandler<Order> {

	public UpdateOrderHandler() {
		this.service = new OrderService();
		this.logger = Logger.getLogger(UpdateOrderHandler.class);
	}
	
	protected Order createHolderEntityFromRequest(Map<String, Object> request) throws Exception {
		String requestBody = (String) request.get("body");
		
		if(requestBody == null) {
			throw new InvalidRequestException();
		}
		
		@SuppressWarnings("unchecked")
		Map<String, String> parameters = (Map<String, String>) request.get("pathParameters");
		
		Order order = objectMapper.readValue(requestBody, Order.class);
		order.setId(Long.parseLong(parameters.get("id")));
		
		return order;
	}

}
