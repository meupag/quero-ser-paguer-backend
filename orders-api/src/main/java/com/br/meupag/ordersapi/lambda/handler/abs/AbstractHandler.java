package com.br.meupag.ordersapi.lambda.handler.abs;

import java.util.Map;

import org.jboss.logging.Logger;

import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.br.meupag.ordersapi.db.service.abs.IService;
import com.br.meupag.ordersapi.domain.abs.Domain;
import com.br.meupag.ordersapi.lambda.response.ApiGatewayResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractHandler<E extends Domain> implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {
	
	protected IService<E> service;
	protected Logger logger;
	protected final ObjectMapper objectMapper = new ObjectMapper();
	
	protected abstract E createHolderEntityFromRequest(Map<String, Object> request) throws Exception;

}
