package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.db.service.OrderService;
import com.br.meupag.ordersapi.domain.Order;
import com.br.meupag.ordersapi.lambda.handler.abs.AbstractListHandler;

public class ListOrdersHandler extends AbstractListHandler<Order> {
	
	public ListOrdersHandler() {
		this.service = new OrderService();
		this.logger = Logger.getLogger(ListOrdersHandler.class);
	}
	
	protected Order createHolderEntityFromRequest(Map<String, Object> request) throws Exception {
		return null;
	}

}
