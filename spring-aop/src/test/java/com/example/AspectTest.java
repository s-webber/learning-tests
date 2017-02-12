package com.example;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = Config.class)
public class AspectTest {
   @Autowired
   private ExampleBean exampleBean;

   @Test
   public void test() {
      assertEquals("abc", exampleBean.exampleMethod("b"));
   }
}
