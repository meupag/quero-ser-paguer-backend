package com.pag;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;
import org.springframework.test.web.servlet.ResultActions;

import com.pag.modelo.Cliente;
import com.pag.modelo.Produto;

public class TestUtils {
	
    public static String obtainAccessToken() throws OAuthSystemException, OAuthProblemException {
  	  
    	String username = "loja-client";
        String secret = "923f5644-9464-40db-9a47-447fc3626dd3";
        String url = "http://localhost:8081/auth/realms/dev/protocol/openid-connect/token";

        OAuthClient client = new OAuthClient(new URLConnectionClient());

        OAuthClientRequest request = OAuthClientRequest.tokenLocation(url)
                .setGrantType(GrantType.CLIENT_CREDENTIALS)
                .setClientId(username)
                .setClientSecret(secret)
                .buildBodyMessage();
         return client.accessToken(request).getAccessToken();
    }
    
    public static Cliente getCliente() throws ParseException {
        Cliente cliente  = new Cliente();
        cliente.setCpf("01234567801");
        cliente.setNascidoEm( new SimpleDateFormat("dd/MM/YYYY").parse("11/02/1988"));
        cliente.setNome("João de Barro");
        return cliente;
    }
    
    public static String getId(ResultActions actions) {
    	String[] location = actions.andReturn().getResponse().getHeader("location").split("/");
    	String id = location[location.length - 1];
    	return id;
    }
    
    public static Produto getProduto() throws ParseException {
       Produto produto = new Produto();
       produto.setNome("SENHOR DOS ANEIS - AS DUAS TORRES");
       produto.setPrecoSugerido(50.9);
        return produto;
    }

}
