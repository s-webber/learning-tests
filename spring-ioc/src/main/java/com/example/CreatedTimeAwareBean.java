package com.example;

/**
 * A plain old Java object (POJO) that keeps a record of the time it was created.
 * <p>
 * Used to confirm the behaviour of the @Lazy annotation.
 *
 * @see Config#eagerBean()
 * @see Config#lazyBean()
 */
public class CreatedTimeAwareBean {
   private final long createdTime;

   public CreatedTimeAwareBean() {
      createdTime = System.nanoTime();
   }

   public long getCreatedTime() {
      return createdTime;
   }
}
