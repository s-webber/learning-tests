package com.example;

import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;

import java.util.function.Supplier;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CacheTest {
   private AnnotationConfigApplicationContext applicationContext;
   private ExampleService exampleService;

   @Before
   public void setUp() {
      applicationContext = new AnnotationConfigApplicationContext("com.example");
      exampleService = applicationContext.getBean(ExampleService.class);
   }

   @After
   public void tearDown() {
      applicationContext.close();
   }

   /** Confirm subsequent calls (with the same argument) to a method annotated with @Cacheable will return the same result. */
   @Test
   public void cacheable_sameArgSameResult() {
      Supplier<Object> s = () -> exampleService.cacheable("a");
      Object o = s.get();
      assertSame(o, s.get());
      assertSame(o, s.get());
      assertSame(o, s.get());
   }

   /** Confirm subsequent calls (with different arguments) to a method annotated with @Cacheable will return different results. */
   @Test
   public void cacheable_differentArgDifferentResult() {
      Object a = exampleService.cacheable("a");
      Object b = exampleService.cacheable("b");
      Object c = exampleService.cacheable("c");
      assertNotSame(a, b);
      assertNotSame(a, c);
      assertNotSame(b, c);
   }

   /** Confirm subsequent calls (with the same argument) to a method annotated with @CachePut will return the same result. */
   @Test
   public void put_sameArgDifferentResult() {
      Supplier<Object> s = () -> exampleService.put("a");
      Object o1 = s.get();
      Object o2 = s.get();
      Object o3 = s.get();
      assertNotSame(o1, o2);
      assertNotSame(o1, o3);
      assertNotSame(o2, o3);
   }

   /**
    * Confirm calling a method annotated with @CacheEvict will cause the next call to a method annotated with @Cacheable (when passed the same argument) will
    * return a new result.
    */
   @Test
   public void evict() {
      String key = "key";
      Supplier<Object> s = () -> exampleService.cacheable(key);

      // consecutive calls to "get", with the same argument, return the same result
      Object o1 = s.get();
      assertSame(o1, s.get());

      // after a call to "evict", the next call to "get" returns a different result for the same argument
      exampleService.evict(key);
      Object o2 = s.get();
      assertNotSame(o1, o2);
      assertSame(o2, s.get());
   }

   /**
    * Confirm calling a method annotated with @CacheEvict will only evict the cache item referenced by the arguments passed to the method.
    */
   @Test
   public void evict_multipleKeys() {
      String key1 = "key1";
      String key2 = "key2";

      Object o1 = exampleService.cacheable(key1);
      Object o2 = exampleService.cacheable(key2);
      assertNotSame(o1, o2);

      assertSame(o1, exampleService.cacheable(key1));
      assertSame(o2, exampleService.cacheable(key2));

      exampleService.evict(key1);

      assertNotSame(o1, exampleService.cacheable(key1));
      assertSame(o2, exampleService.cacheable(key2));
   }
}
