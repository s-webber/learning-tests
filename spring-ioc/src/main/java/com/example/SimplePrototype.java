package com.example;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** Example of a prototype-scoped component. */
@Component
@Scope("prototype")
public class SimplePrototype {
   private Object state;

   public Object getState() {
      return state;
   }

   public void setState(Object state) {
      this.state = state;
   }
}
