package com.example;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ExampleConfig.class)
public class AspectTest {
   @Autowired
   private ExampleBean exampleBean;

   /**
    * ExampleBean instances created using "new" will not have ExampleAspect applied to them.
    */
   @Test
   public void newlyCreatedBean() {
      assertEquals("ab", new ExampleBean().exampleMethod("b"));
   }

   /**
    * The ExampleBean instance defined in ExampleConfig#exampleBean, and auto-wired into this test, will have ExampleAspect applied to it.
    */
   @Test
   public void autowiredBean() {
      assertEquals("abc", exampleBean.exampleMethod("b"));
   }
}
