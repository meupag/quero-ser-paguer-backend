package br.pag.config;

import java.util.ArrayList;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("br.pag.endpoint"))
                .paths(PathSelectors.regex("/.*"))
                .build()
                .apiInfo(metaInfo()).tags(new Tag("Customer-End-Point", "Endpoint REST dos Clientes"),
                new Tag("ItemOrder-End-Point", "Endpoint REST dos Pedido-Itens"),
                new Tag("Order-End-Point", "Endpoint REST dos Pedidos"),
                new Tag("Product-End-Point", "Endpoint REST dos Produtos"));
    }

    private ApiInfo metaInfo() {
        ApiInfo apiInfo = new ApiInfo(
                "PAG API",
                "API feita para o Teste da PAG",
                "1.0",
                "",
                new Contact("Brunner", "", "brunnerk@gmail.com"),
                "",
                "",
                new ArrayList<>());
        return apiInfo;
    }
}
