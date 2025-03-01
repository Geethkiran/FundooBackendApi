package com.fundooproject.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * This is configuration for swagger,
 *
 */

@Configuration
@EnableSwagger2
public class SwaggerConfig {

	/**
	 * we are creating Bean of {@link Docket} by passing our basepackage of
	 * project(com.fundooproject) for running our APIs.
	 */
	@Bean
	public Docket productApi() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.fundooproject")).build();
	}
}