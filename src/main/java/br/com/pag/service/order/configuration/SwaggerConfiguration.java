package br.com.pag.service.order.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

@Configuration
@EnableSwagger2
public class SwaggerConfiguration {
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.pag.service.order.controller"))
            .paths(PathSelectors.regex("/.*"))
            .build()
            .useDefaultResponseMessages(false)
            .apiInfo(apiInfo());
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo(
            "Pag! Orders API",
            "This API was built to manage Orders from Pag!.",
            "1.0",
            "http://terms.service.io",
            contact(),
            "Apache 2.0 license",
            "https://www.apache.org/licenses/LICENSE-2.0",
            new ArrayList<>());
        return apiInfo;
    }

    private Contact contact() {
        return new Contact("Marcos R Oliveira",
            "https://mr0ger-arduino.blogspot.com/",
            "mroger.oliveira@gmail.com");
    }
}
