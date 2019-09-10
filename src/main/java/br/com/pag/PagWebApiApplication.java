package br.com.pag;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class PagWebApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(PagWebApiApplication.class, args);
	}

}
