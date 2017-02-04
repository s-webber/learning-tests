package com.example;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** Example of a singleton-scoped component. */
@Component
@Scope("singleton")
public class SimpleSingleton {
   private Object state;

   public Object getState() {
      return state;
   }

   public void setState(Object state) {
      this.state = state;
   }
}
