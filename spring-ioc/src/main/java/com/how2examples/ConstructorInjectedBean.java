package com.how2examples;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/** Example of a singleton-scoped component autowired using constructor injection. */
@Component
public class ConstructorInjectedBean {
   private final AutowiredSingletonBean autowiredSingletonBean;
   private final PlainBean plainBean;

   @Autowired
   public ConstructorInjectedBean(AutowiredSingletonBean autowiredSingletonBean, PlainBean plainBean) {
      this.autowiredSingletonBean = autowiredSingletonBean;
      this.plainBean = plainBean;
   }

   public AutowiredSingletonBean getAutowiredSingletonBean() {
      return autowiredSingletonBean;
   }

   public PlainBean getPlainBean() {
      return plainBean;
   }
}
