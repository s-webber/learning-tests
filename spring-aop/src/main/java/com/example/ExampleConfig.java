package com.example;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
public class ExampleConfig {
   @Bean
   public ExampleBean exampleBean() {
      return new ExampleBean();
   }

   @Bean
   public ExampleAspect exampleAspect() {
      return new ExampleAspect();
   }
}
