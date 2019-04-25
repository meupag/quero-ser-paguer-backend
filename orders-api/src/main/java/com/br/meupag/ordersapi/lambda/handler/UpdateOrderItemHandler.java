package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.db.service.OrderItemService;
import com.br.meupag.ordersapi.domain.OrderItem;
import com.br.meupag.ordersapi.lambda.handler.abs.AbstractUpdateHandler;
import com.br.meupag.ordersapi.lambda.handler.exception.InvalidRequestException;

public class UpdateOrderItemHandler extends AbstractUpdateHandler<OrderItem> {

	public UpdateOrderItemHandler() {
		this.service = new OrderItemService();
		this.logger = Logger.getLogger(UpdateOrderItemHandler.class);
	}
	
	protected OrderItem createHolderEntityFromRequest(Map<String, Object> request) throws Exception {
		String requestBody = (String) request.get("body");
		
		if(requestBody == null) {
			throw new InvalidRequestException();
		}
		
		@SuppressWarnings("unchecked")
		Map<String, String> parameters = (Map<String, String>) request.get("pathParameters");
		
		OrderItem orderItem = objectMapper.readValue(requestBody, OrderItem.class);
		orderItem.setId(Long.parseLong(parameters.get("id")));
		
		return orderItem;
	}

}
