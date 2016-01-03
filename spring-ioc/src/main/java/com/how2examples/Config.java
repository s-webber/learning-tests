package com.how2examples;

import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;

/**
 * Contains bean definitions.
 *
 * @see http://docs.spring.io/spring/docs/current/spring-framework-reference/html/beans.html#beans-java
 */
@Configuration
public class Config {
   /** Example of a bean definition that has the bean name specified by its annotation. */
   @Bean(name = "nameSpecifiedByAnnotation", destroyMethod = "shutdown", initMethod = "init", autowire = Autowire.BY_TYPE)
   public PlainBean namedAfterMethodBean() {
      return new PlainBean();
   }

   /** Example of a bean definition that is marked with a @Lazy annotation. */
   @Bean
   @Lazy
   public CreatedTimeAwareBean lazyBean() {
      return new CreatedTimeAwareBean();
   }

   /** Example of a bean definition that is <i>not</i> marked with a @Lazy annotation. */
   @Bean
   public CreatedTimeAwareBean eagerBean() {
      return new CreatedTimeAwareBean();
   }
}
