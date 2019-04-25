package com.br.meupag.ordersapi.lambda.handler;

import java.util.Map;

import javax.persistence.NoResultException;

import org.jboss.logging.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.br.meupag.ordersapi.lambda.response.ApiGatewayResponse;
import com.br.meupag.ordersapi.lambda.response.Response;

public class LoginHandler implements RequestHandler<Map<String, Object>, ApiGatewayResponse> {

	private Logger logger = Logger.getLogger(LoginHandler.class);
	
	@Override
	public ApiGatewayResponse handleRequest(Map<String, Object> request, Context context) {
		
		try {	

			
			return ApiGatewayResponse.builder()
					.setStatusCode(200)
					.setObjectBody(null)
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
