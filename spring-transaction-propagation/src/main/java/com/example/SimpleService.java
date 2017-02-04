package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SimpleService {
   @Autowired
   private SimpleRepository myDao;

   /** Support a current transaction, throw an exception if none exists. */
   @Transactional(propagation = Propagation.MANDATORY)
   public void mandatory(MyRunnable... runnables) throws Exception {
      process(runnables);
   }

   /** Execute within a nested transaction if a current transaction exists, behave like PROPAGATION_REQUIRED else. */
   @Transactional(propagation = Propagation.NESTED)
   public void nested(MyRunnable... runnables) throws Exception {
      process(runnables);
   }

   /** Execute non-transactionally, throw an exception if a transaction exists. */
   @Transactional(propagation = Propagation.NEVER)
   public void never(MyRunnable... runnables) throws Exception {
      process(runnables);
   }

   /** Execute non-transactionally, suspend the current transaction if one exists. */
   @Transactional(propagation = Propagation.NOT_SUPPORTED)
   public void notSupported(MyRunnable... runnables) throws Exception {
      process(runnables);
   }

   /** Support a current transaction, create a new one if none exists. */
   @Transactional(propagation = Propagation.REQUIRED)
   public void required(MyRunnable... runnables) throws Exception {
      process(runnables);
   }

   /** Create a new transaction, and suspend the current transaction if one exists. */
   @Transactional(propagation = Propagation.REQUIRES_NEW)
   public void requiresNew(MyRunnable... runnables) throws Exception {
      process(runnables);
   }

   /** Support a current transaction, execute non-transactionally if none exists. */
   @Transactional(propagation = Propagation.SUPPORTS)
   public void supports(MyRunnable... runnables) throws Exception {
      process(runnables);
   }

   private void process(MyRunnable[] runnables) throws Exception {
      for (MyRunnable r : runnables) {
         r.run();
      }
   }
}
