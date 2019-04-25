package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.db.service.OrderItemService;
import com.br.meupag.ordersapi.domain.OrderItem;
import com.br.meupag.ordersapi.lambda.handler.abs.AbstractListHandler;

public class ListOrderItemsHandler extends AbstractListHandler<OrderItem> {
	
	public ListOrderItemsHandler() {
		this.service = new OrderItemService();
		this.logger = Logger.getLogger(ListOrderItemsHandler.class);
	}
	
	protected OrderItem createHolderEntityFromRequest(Map<String, Object> request) throws Exception {
		return null;
	}

}
