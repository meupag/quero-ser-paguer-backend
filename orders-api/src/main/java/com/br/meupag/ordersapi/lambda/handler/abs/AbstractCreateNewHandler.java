package com.br.meupag.ordersapi.lambda.handler.abs;

import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.br.meupag.ordersapi.domain.abs.Domain;
import com.br.meupag.ordersapi.lambda.response.ApiGatewayResponse;
import com.br.meupag.ordersapi.lambda.response.Response;

public abstract class AbstractCreateNewHandler<E extends Domain> extends AbstractHandler<E> {
	
	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> request, Context context) {
		
		try {			
			E entity = this.createHolderEntityFromRequest(request);
			entity = service.createNew(entity);	
			
			return ApiGatewayResponse.builder()
					.setStatusCode(200)
					.setObjectBody(entity)
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
