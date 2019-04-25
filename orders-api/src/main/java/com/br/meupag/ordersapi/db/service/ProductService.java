package com.br.meupag.ordersapi.db.service;

import com.br.meupag.ordersapi.db.service.abs.AbstractService;
import com.br.meupag.ordersapi.domain.Product;

public class ProductService extends AbstractService<Product> {

	protected void cleanRecursiveReferences(Product product) throws Exception {
	
	}
	
	protected void merge(Product oldProduct, Product newProduct) throws Exception {
		oldProduct.setName(newProduct.getName() != null ? newProduct.getName() : oldProduct.getName());
		oldProduct.setSuggestedPrice(newProduct.getSuggestedPrice() != null ? newProduct.getSuggestedPrice() 
				: oldProduct.getSuggestedPrice());
	
	}
	
	protected String getFindAllQuery() {
		return "SELECT p FROM Product p";
	}
	
}
