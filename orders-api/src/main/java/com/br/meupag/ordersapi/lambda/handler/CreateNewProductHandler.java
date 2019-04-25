package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import org.jboss.logging.Logger;

import com.br.meupag.ordersapi.db.service.ProductService;
import com.br.meupag.ordersapi.domain.Product;
import com.br.meupag.ordersapi.lambda.handler.abs.AbstractCreateNewHandler;
import com.br.meupag.ordersapi.lambda.handler.exception.InvalidRequestException;

public class CreateNewProductHandler extends AbstractCreateNewHandler<Product> {
	
	public CreateNewProductHandler() {
		this.service = new ProductService();
		this.logger = Logger.getLogger(CreateNewProductHandler.class);
	}
	
	protected Product createHolderEntityFromRequest(Map<String, Object> request) throws Exception {
		String requestBody = (String) request.get("body");
		
		if(requestBody == null) {
			throw new InvalidRequestException();
		}
		
		return objectMapper.readValue(requestBody, Product.class);
	}

}
