package com.how2examples;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;

@SpringBootApplication
@EnableSwagger
public class Application {
   public static void main(String[] args) {
      SpringApplication.run(Application.class, args);
   }

   @Bean
   public SwaggerSpringMvcPlugin swaggerPlugin(SpringSwaggerConfig swaggerConfig) {
      ApiInfo apiInfo = new ApiInfo("Spring Boot Swagger Example", "Example of integrating Swagger with a Spring Boot REST service", null, null, null, null);
      return new SwaggerSpringMvcPlugin(swaggerConfig).apiInfo(apiInfo).includePatterns("/example.*?").useDefaultResponseMessages(false);
   }
}
