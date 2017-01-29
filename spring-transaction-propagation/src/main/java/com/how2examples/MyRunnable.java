package com.how2examples;

/**
 * Like {@code java.lang.Runnable}, but does allow checked exceptions to be thrown.
 * <p>
 * Similar to {@code util.concurrent.Callable}, but does not return a result.
 */
@FunctionalInterface
public interface MyRunnable {
   void run() throws Exception;
}
