package com.queroserpaguerbackend.apirest.swagger;

import springfox.documentation.swagger2.annotations.EnableSwagger2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

import static springfox.documentation.builders.PathSelectors.regex;

import java.awt.List;
import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public Docket queroserpaguerApi() {
		return new Docket(DocumentationType.SWAGGER_2)
				.select()
				.apis(RequestHandlerSelectors.basePackage("com.queroserpaguerbackend.apirest"))
				.paths(regex("/api.*"))
				.build()
				.apiInfo(metaInfo()
		);
	}
	
	private ApiInfo metaInfo() {
		ApiInfo apiInfo = new ApiInfo(
				"Quero ser paguer backend API REST",
				"API REST baseada no desafio do processo seletivo MeuPag",
				"1.0",
				"Termos do serviço",
						"Vitor Braga "+ 
						"https://www.vittorbraga.com.br "+ 
						"vittor_braga@hotmail.com ",
				"Apache Licence Version 2.0",
				"https://www.apache.org/license.html"
		);
		return apiInfo;
	}
}
