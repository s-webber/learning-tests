package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class CustomerRepositoryTest {
   private AnnotationConfigApplicationContext applicationContext;
   private CustomerRepository customerRepository;

   @Before
   public void setUp() {
      applicationContext = new AnnotationConfigApplicationContext("com.example");
      customerRepository = applicationContext.getBean(CustomerRepository.class);
   }

   @After
   public void tearDown() {
      applicationContext.close();
   }

   @Test
   public void test() {
      Customer customer = customerRepository.findById(1).get();
      Name name = customer.getName();
      assertNotNull(name);
      assertEquals("Fred", name.getFirstName());
      assertEquals("Heslop", name.getLastName());
   }
}
