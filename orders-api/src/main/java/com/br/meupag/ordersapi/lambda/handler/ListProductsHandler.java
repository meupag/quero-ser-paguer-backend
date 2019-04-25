package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.db.service.ProductService;
import com.br.meupag.ordersapi.domain.Product;
import com.br.meupag.ordersapi.lambda.handler.abs.AbstractListHandler;

public class ListProductsHandler extends AbstractListHandler<Product> {
	
	public ListProductsHandler() {
		this.service = new ProductService();
		this.logger = Logger.getLogger(ListProductsHandler.class);
	}
	
	protected Product createHolderEntityFromRequest(Map<String, Object> request) throws Exception {
		return null;
	}

}
