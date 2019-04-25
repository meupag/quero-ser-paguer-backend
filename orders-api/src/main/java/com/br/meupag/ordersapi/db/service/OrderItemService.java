package com.br.meupag.ordersapi.db.service;

import com.br.meupag.ordersapi.db.service.abs.AbstractService;
import com.br.meupag.ordersapi.domain.OrderItem;

public class OrderItemService extends AbstractService<OrderItem> {
	
	protected void cleanRecursiveReferences(OrderItem orderItem) throws Exception {
		orderItem.getOrder().setOrderItems(null);
		orderItem.getOrder().getClient().setOrders(null);
	}
	
	protected void merge(OrderItem oldOrderItem, OrderItem newOrderItem) throws Exception {
		oldOrderItem.setQuantity(newOrderItem.getQuantity() != null ? newOrderItem.getQuantity() : oldOrderItem.getQuantity());
		oldOrderItem.setPrice(newOrderItem.getPrice() != null ? newOrderItem.getPrice() : oldOrderItem.getPrice());
	}
	
	protected String getFindAllQuery() {
		return "SELECT oi FROM OrderItem oi";
	}
	
}
