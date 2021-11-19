package org.serratec.ecommerce.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
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
				.apis(RequestHandlerSelectors.basePackage("org.serratec.ecomerce")) //Em que lugar será exibido a documentação
				.paths(PathSelectors.any())				
				.build()
				.apiInfo(this.apiInfo());
	}
	
	//Personalização da documentação
	public ApiInfo apiInfo() {
		
		ApiInfo apiInfo = 
				new ApiInfoBuilder()
				.title("Ecommerce API")
				.description("Esta é uma API para trabalho final da matéria de RestAPI.")
				.license("Apache 2.0")
				.version("2.10.5")
				.contact(new Contact("Serratec", "http://www.serratec.org", "igorvh21@gmail.com"))
				.build();
		
		return apiInfo;
	}
}
