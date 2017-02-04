package com.example;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcOperations;

public class NamedParameterJdbcTemplateTest {
   private AnnotationConfigApplicationContext applicationContext;
   private NamedParameterJdbcOperations template;

   @Before
   public void setUp() {
      applicationContext = new AnnotationConfigApplicationContext("com.example");
      template = applicationContext.getBean(NamedParameterJdbcOperations.class);
   }

   @After
   public void tearDown() {
      applicationContext.close();
   }

   @Test
   public void queryForMapUsingMapSqlParameterSource() {
      MapSqlParameterSource s = new MapSqlParameterSource();
      s.addValue("name", "bbb");
      Map<String, Object> m = template.queryForMap("SELECT id, name FROM employee WHERE name=:name", s);
      assertEquals(2, m.size());
      assertEquals(2, m.get("id"));
      assertEquals("bbb", m.get("name"));
   }

   @Test
   public void queryForMapUsingMap() {
      Map<String, Object> paramMap = new HashMap<>();
      paramMap.put("name", "bbb");
      Map<String, Object> m = template.queryForMap("SELECT id, name FROM employee WHERE name=:name", paramMap);
      assertEquals(2, m.size());
      assertEquals(2, m.get("id"));
      assertEquals("bbb", m.get("name"));
   }
}
