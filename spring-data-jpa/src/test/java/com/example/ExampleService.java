package com.example;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ExampleService {
   /** Support a current transaction, create a new one if none exists. */
   @Transactional(propagation = Propagation.REQUIRED)
   public void required(Runnable runnable) {
      runnable.run();
   }

   /** Create a new transaction, and suspend the current transaction if one exists. */
   @Transactional(propagation = Propagation.REQUIRES_NEW)
   public void requiresNew(Runnable runnable) {
      runnable.run();
   }
}
