package com.tejacodes.blogapplication.config;

import java.util.Collections;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

@Configuration
public class SwaggerConfig {
	
	public ApiInfo apiInfo()
	{
		return new ApiInfo(
				"Spring Boot Blog Rest APIs",
				"Spring Boot Blog Rest APIs Description",
				"1",
				"Terms of Service",
				new Contact("Teja Cherukuri", "www.abc.net", "teja@gmail.com"),
				"License of API",
				"API License URL",
				Collections.emptyList()
				);
				
	}
	
	@Bean
	public Docket api()
	{
		return new Docket(DocumentationType.SWAGGER_2)
				.apiInfo(apiInfo())
				.select()
				.apis(RequestHandlerSelectors.any())
				.paths(PathSelectors.any())
				.build();
	}
	
}
