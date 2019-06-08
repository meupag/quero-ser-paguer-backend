package com.pag.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.oauth2.resource.ResourceServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.fasterxml.jackson.databind.ObjectMapper;
@Configuration
@EnableWebSecurity
@EnableResourceServer
@EnableGlobalMethodSecurity(prePostEnabled = true)
@ConditionalOnProperty(prefix = "rest.security", value = "enabled", havingValue = "true")
@Import({SegConf.class})
public class Seguranca  extends ResourceServerConfigurerAdapter {
  @Autowired
  private ResourceServerProperties resourceServerProperties;

  @Autowired
  private SegConf segConfiguracoes;

  @Override
  public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
    resources.resourceId(resourceServerProperties.getResourceId());
  }

  @Override
  public void configure(final HttpSecurity http) throws Exception {

    http.cors()
        .configurationSource(corsConfigurationSource())
        .and()
        .headers()
        .frameOptions()
        .disable()
        .and()
        .csrf()
        .disable()
        .authorizeRequests()
        .antMatchers(segConfiguracoes.getApiMatcher())
        .authenticated();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    if (null != segConfiguracoes.getCorsConfiguration()) {
      source.registerCorsConfiguration("/**", segConfiguracoes.getCorsConfiguration());
    }
    return source;
  }

  @Bean
  public AcessoToken acessoToken(ObjectMapper mapper) {
    return new AcessoToken(mapper);
  }

  @Configuration
  @ConditionalOnProperty(prefix = "security.oauth2.client", value = "grant-type", havingValue = "client_credentials")
  public static class OAuthRestTemplateConfigurer {

    @Bean
    public OAuth2RestTemplate oauth2RestTemplate(OAuth2ProtectedResourceDetails details) {
      OAuth2RestTemplate oAuth2RestTemplate = new OAuth2RestTemplate(details);
      oAuth2RestTemplate.getAccessToken();
      return oAuth2RestTemplate;
    }
  }
}