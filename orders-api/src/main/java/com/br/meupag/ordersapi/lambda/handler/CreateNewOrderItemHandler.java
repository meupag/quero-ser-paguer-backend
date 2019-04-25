package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.db.service.OrderItemService;
import com.br.meupag.ordersapi.domain.OrderItem;
import com.br.meupag.ordersapi.lambda.handler.abs.AbstractCreateNewHandler;
import com.br.meupag.ordersapi.lambda.handler.exception.InvalidRequestException;

public class CreateNewOrderItemHandler extends AbstractCreateNewHandler<OrderItem> {

	public CreateNewOrderItemHandler() {
		this.service = new OrderItemService();
		this.logger = Logger.getLogger(CreateNewOrderItemHandler.class);
	}
	
	protected OrderItem createHolderEntityFromRequest(Map<String, Object> request) throws Exception {
		String requestBody = (String) request.get("body");
		
		if(requestBody == null) {
			throw new InvalidRequestException();
		}
		
		return objectMapper.readValue(requestBody, OrderItem.class);
	}
	
}
