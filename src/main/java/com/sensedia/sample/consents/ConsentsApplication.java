package com.sensedia.sample.consents;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(
		info = @Info(
				title = "Documentação REST API de Consentimento",
				description = "Documentação detalhada para a REST API de Consentimento",
				version = "v1",
				contact = @Contact(
						name = "Rodrigo Feitosa",
						email = "rodrigofeitosarodrigues@gmail.com",
						url = "https://github.com/Rodrigo2898"
				)
		)
)
public class ConsentsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ConsentsApplication.class, args);
	}

}
