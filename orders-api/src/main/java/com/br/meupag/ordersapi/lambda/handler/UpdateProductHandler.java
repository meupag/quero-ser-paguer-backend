package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.db.service.ProductService;
import com.br.meupag.ordersapi.domain.Product;
import com.br.meupag.ordersapi.lambda.handler.abs.AbstractUpdateHandler;
import com.br.meupag.ordersapi.lambda.handler.exception.InvalidRequestException;

public class UpdateProductHandler extends AbstractUpdateHandler<Product> {

	public UpdateProductHandler() {
		this.service = new ProductService();
		this.logger = Logger.getLogger(UpdateProductHandler.class);
	}
	
	protected Product createHolderEntityFromRequest(Map<String, Object> request) throws Exception {
		String requestBody = (String) request.get("body");
		
		if(requestBody == null) {
			throw new InvalidRequestException();
		}
		
		@SuppressWarnings("unchecked")
		Map<String, String> parameters = (Map<String, String>) request.get("pathParameters");
		
		Product product = objectMapper.readValue(requestBody, Product.class);
		product.setId(Long.parseLong(parameters.get("id")));
		
		return product;
	}

}
