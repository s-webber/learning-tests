package com.how2examples;

/**
 * Keeps track of the order in in which events have occurred.
 * <p>
 * Used by unit tests to confirm behaviour.
 */
public class LifeCycleRecorder {
   private final StringBuilder sb = new StringBuilder();

   public void record(String event) {
      if (sb.length() > 0) {
         sb.append('|');
      }
      sb.append(event);
   }

   @Override
   public String toString() {
      return sb.toString();
   }
}
