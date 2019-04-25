
package com.br.meupag.ordersapi.db.service;

import com.br.meupag.ordersapi.db.service.abs.AbstractService;
import com.br.meupag.ordersapi.domain.Order;
import com.br.meupag.ordersapi.domain.OrderItem;

public class OrderService extends AbstractService<Order> {

	protected void cleanRecursiveReferences(Order order) throws Exception {
		order.getClient().setOrders(null);
		
		for(OrderItem orderItem : order.getOrderItems()) {
			orderItem.setOrder(null);
		}
	}
	
	protected void merge(Order oldOrder, Order newOrder) throws Exception {
		oldOrder.setValue(newOrder.getValue() != null ? newOrder.getValue() : oldOrder.getValue());
	}
	
	protected String getFindAllQuery() {
		return "SELECT o FROM Order o";
	}
	
}
