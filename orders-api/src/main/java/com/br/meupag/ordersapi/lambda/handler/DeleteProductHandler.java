package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.db.service.ProductService;
import com.br.meupag.ordersapi.domain.Product;
import com.br.meupag.ordersapi.lambda.handler.abs.AbstractDeleteHandler;

public class DeleteProductHandler extends AbstractDeleteHandler<Product> {
	
	public DeleteProductHandler() {
		this.service = new ProductService();
		this.logger = Logger.getLogger(DeleteProductHandler.class);
	}
	
	protected Product createHolderEntityFromRequest(Map<String, Object> request) throws Exception {
		@SuppressWarnings("unchecked")
		Map<String, String> parameters = (Map<String, String>) request.get("pathParameters");
		
		Product product = new Product();
		product.setId(Long.parseLong(parameters.get("id")));
		
		return product;
	}

}
