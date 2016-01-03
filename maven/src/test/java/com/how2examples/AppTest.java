package com.how2examples;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/** Used to provide Jacoco test coverage plug-in something to work with. */
public class AppTest {
   /** Purposefully only test with {@code true} argument to demonstrate Jacoco's "missed branch" reporting. */
   @Test
   public void test() {
      assertEquals("yes", new App().toYesNo(true));
   }
}
