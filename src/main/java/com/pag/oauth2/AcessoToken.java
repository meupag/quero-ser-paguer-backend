package com.pag.oauth2;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.oauth2.resource.JwtAccessTokenConverterConfigurer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AcessoToken  extends DefaultAccessTokenConverter implements JwtAccessTokenConverterConfigurer {
	  private static final String CLIENT_NAME_ELEMENT_IN_JWT = "resource_access";
	  private static final String ROLE_ELEMENT_IN_JWT = "roles";
	  private ObjectMapper mapper;

	  @Autowired
	  public AcessoToken(ObjectMapper mapper) {
	    this.mapper = mapper;
	  }

	  @Override
	  public void configure(JwtAccessTokenConverter converter) {
	    converter.setAccessTokenConverter(this);
	  }


	  @Override
	  public OAuth2Authentication extractAuthentication(Map<String, ?> tokenMap) {
	    JsonNode token = mapper.convertValue(tokenMap, JsonNode.class);
	    Set<String> audienceList = extractClients(token); 
	    List<GrantedAuthority> authorities = extractRoles(token); 

	    OAuth2Authentication authentication = super.extractAuthentication(tokenMap);
	    OAuth2Request oAuth2Request = authentication.getOAuth2Request();

	    OAuth2Request request =  new OAuth2Request(oAuth2Request.getRequestParameters(),
	    											oAuth2Request.getClientId(), authorities, true, oAuth2Request.getScope(),
	    											audienceList, null, null, null);

	    Authentication usernamePasswordAuthentication = new UsernamePasswordAuthenticationToken(authentication.getPrincipal(), "N/A", authorities);
	    return new OAuth2Authentication(request, usernamePasswordAuthentication);
	  }

	  private List<GrantedAuthority> extractRoles(JsonNode jwt) {
	    Set<String> rolesWithPrefix = new HashSet<>();

	    jwt.path(CLIENT_NAME_ELEMENT_IN_JWT)
	        .elements()
	        .forEachRemaining(e -> e.path(ROLE_ELEMENT_IN_JWT)
	            .elements()
	            .forEachRemaining(r -> rolesWithPrefix.add("ROLE_" + r.asText())));

	    final List<GrantedAuthority> authorityList = AuthorityUtils.createAuthorityList(rolesWithPrefix.toArray(new String[0]));
	    return authorityList;
	  }

	  private Set<String> extractClients(JsonNode jwt) {
	    if (jwt.has(CLIENT_NAME_ELEMENT_IN_JWT)) {
	      JsonNode resourceAccessJsonNode = jwt.path(CLIENT_NAME_ELEMENT_IN_JWT);
	      final Set<String> clientNames = new HashSet<>();
	      resourceAccessJsonNode.fieldNames()
	          .forEachRemaining(clientNames::add);

	      return clientNames;

	    } else {
	      throw new IllegalArgumentException("Nao foi encontrado  " + CLIENT_NAME_ELEMENT_IN_JWT + " no token");
	    }
	  }
}