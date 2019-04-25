package com.br.meupag.ordersapi.lambda.handler.abs;

import java.util.List;
import java.util.Map;

import javax.persistence.NoResultException;

import com.amazonaws.services.lambda.runtime.Context;
import com.br.meupag.ordersapi.domain.abs.Domain;
import com.br.meupag.ordersapi.lambda.response.ApiGatewayResponse;
import com.br.meupag.ordersapi.lambda.response.Response;

public abstract class AbstractListHandler<E extends Domain> extends AbstractHandler<E> {
	
	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> request, Context context) {
		
		try {		
			List<E> result = service.findAll();
			
			return ApiGatewayResponse.builder()
					.setStatusCode(200)
					.setObjectBody(result)
					.build();					
		} catch(NoResultException e) {		
			logger.error(e);
			
			Response responseBody = new Response(e.getMessage(), request);
			
			return ApiGatewayResponse.builder()
					.setStatusCode(404)
					.setObjectBody(responseBody)
					.build();
		} catch(Exception e) {		
			logger.error(e);
			
			Response responseBody = new Response(e.getMessage(), request);
			
			return ApiGatewayResponse.builder()
					.setStatusCode(500)
					.setObjectBody(responseBody)
					.build();
		}
		
	}
	
}
