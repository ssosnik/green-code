package com.ssosnik.greencode.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SpringDocConfiguration {

	@Value("${build.version}")
	private String buildVersion;

	@Bean(name = "com.ssosnik.greencode.configuration.SpringDocConfiguration.apiInfo")
	OpenAPI apiInfo() {
		Contact contact = new Contact();
		contact.setName("Sebastian Sosnik");
		contact.setUrl("https://www.ing.pl/pionteching");
		contact.setEmail("s.sosnik@gmail.com");

		return new OpenAPI().info(new Info().title("Green Code Services").description(
				"Offers 3 end-points for the competition \"Zielona Tesla za Zielony kod\". The code was generated by Openapi Generator https://github.com/openapitools/openapi-generator and the upgraded to newest spring and springdoc version.")
				.version(buildVersion));
	}
}