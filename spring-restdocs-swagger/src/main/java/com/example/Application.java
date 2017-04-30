package com.example;

import static springfox.documentation.builders.PathSelectors.regex;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class Application {
   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }

   @Bean
   public Docket exampleApi() {
      return new Docket(DocumentationType.SWAGGER_2).useDefaultResponseMessages(false).groupName("example").apiInfo(apiInfo()).select()
            .paths(regex("/example.*?.*")).build();
   }

   private ApiInfo apiInfo() {
      return new ApiInfoBuilder().title("Example of Documenting a Spring REST Service")
            .description("Example of documenting a Spring Boot REST service using Swagger and Spring REST Docs.").build();
   }
}
