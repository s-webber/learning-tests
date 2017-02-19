package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;

/**
 * Provides examples of JdbcOperations/JdbcTemplate using an in-memory database created from src/test/resources/db-schema.sql
 */
public class JdbcTemplateTest {
   private static final RowMapper<ExampleBean> ROW_MAPPER = (rs, rowNum) -> new ExampleBean(rs.getInt("id"), rs.getString("name"),
         rs.getTimestamp("start_date"));

   private AnnotationConfigApplicationContext applicationContext;
   private JdbcOperations jdbcOperations;

   @Before
   public void setUp() {
      // need to create a new context before each test in order to ensure that the test database is in a freshly initialised state for each test
      applicationContext = new AnnotationConfigApplicationContext("com.example");
      jdbcOperations = applicationContext.getBean(JdbcOperations.class);
   }

   @After
   public void tearDown() {
      applicationContext.close();
   }

   @Test
   public void queryForList() {
      List<String> names = jdbcOperations.queryForList("SELECT name FROM example_table", String.class);
      assertEquals(2, names.size());
      assertEquals("aaa", names.get(0));
      assertEquals("bbb", names.get(1));
   }

   @Test
   public void queryForList_empty() {
      List<String> names = jdbcOperations.queryForList("SELECT name FROM example_table WHERE id=?", String.class, -1);
      assertTrue(names.isEmpty());
   }

   @Test
   public void query() {
      List<ExampleBean> names = jdbcOperations.query("SELECT id, name, start_date FROM example_table", ROW_MAPPER);
      assertEquals(2, names.size());
      assertEquals("aaa", names.get(0).getName());
      assertEquals("bbb", names.get(1).getName());
   }

   @Test
   public void queryForObject() {
      int count = jdbcOperations.queryForObject("SELECT COUNT(*) FROM example_table", Integer.class);
      assertEquals(2, count);
   }

   @Test(expected = IncorrectResultSizeDataAccessException.class)
   public void queryForObject_moreThanOneRow() {
      jdbcOperations.queryForObject("SELECT name FROM example_table", String.class);
   }

   @Test(expected = IncorrectResultSizeDataAccessException.class)
   public void queryForObject_noRow() {
      jdbcOperations.queryForObject("SELECT name FROM example_table WHERE id=?", String.class, -1);
   }

   @Test
   public void queryForMap() {
      int id = 2;
      Map<String, Object> m = jdbcOperations.queryForMap("SELECT id, name, start_date FROM example_table WHERE id=?", id);
      assertEquals(3, m.size());
      assertEquals(id, m.get("id"));
      assertEquals("bbb", m.get("name"));
      assertSame(Timestamp.class, m.get("start_date").getClass()); // TODO check value
   }

   @Test(expected = IncorrectResultSizeDataAccessException.class)
   public void queryForMap_moreThanOneRow() {
      jdbcOperations.queryForMap("SELECT id, name, start_date FROM example_table");
   }

   @Test(expected = IncorrectResultSizeDataAccessException.class)
   public void queryForMap_noRow() {
      jdbcOperations.queryForMap("SELECT id, name, start_date FROM example_table WHERE id=?", -1);
   }

   @Test
   public void queryForRowSet() {
      SqlRowSet m = jdbcOperations.queryForRowSet("SELECT id, name, start_date FROM example_table");

      assertTrue(m.isBeforeFirst());
      assertFalse(m.isAfterLast());
      assertTrue(m.next());
      assertTrue(m.isFirst());
      assertFalse(m.isLast());
      assertEquals("aaa", m.getString("name"));

      assertFalse(m.isBeforeFirst());
      assertFalse(m.isAfterLast());
      assertTrue(m.next());
      assertFalse(m.isFirst());
      assertTrue(m.isLast());
      assertEquals("bbb", m.getString("name"));

      assertFalse(m.isBeforeFirst());
      assertFalse(m.isAfterLast());
      assertFalse(m.next());

      assertTrue(m.isAfterLast());

      assertTrue(m.first());
      assertTrue(m.isFirst());
      assertFalse(m.isLast());
      assertEquals("aaa", m.getString("name"));
   }

   @Test
   public void queryForRowSet_noRow() {
      SqlRowSet m = jdbcOperations.queryForRowSet("SELECT id, name, start_date FROM example_table WHERE id=?", -1);
      assertFalse(m.isBeforeFirst());
      assertFalse(m.next());
      assertFalse(m.first());
   }

   @Test
   public void queryForRowSet_InvalidResultSetAccessException() {
      SqlRowSet m = jdbcOperations.queryForRowSet("SELECT id, name, start_date FROM example_table");
      String expectedMessage = "Invalid cursor position; nested exception is java.sql.SQLException: Invalid cursor position";
      assertException(InvalidResultSetAccessException.class, expectedMessage, () -> m.getString("name"));
   }

   @Test
   public void insert() {
      int id = 3;
      String name = "test";
      Timestamp startDate = new Timestamp(System.currentTimeMillis());
      assertNotFound(id);
      assertEquals(1, jdbcOperations.update("INSERT INTO example_table (id, name, start_date) VALUES (?, ?, ?)", id, name, startDate));

      ExampleBean e = findById(id);
      assertEquals(id, e.getId());
      assertEquals(name, e.getName());
      assertEquals(startDate, e.getStartDate());
   }

   @Test
   public void update_singleRow() {
      int id = 2;
      String newName = "updated";
      assertEquals("bbb", findById(id).getName());
      assertEquals(1, jdbcOperations.update("UPDATE example_table SET name=? WHERE id=?", newName, id));
      assertEquals(newName, findById(id).getName());
   }

   @Test
   public void update_multipleRows() {
      String newName = "updated";
      assertEquals(2, jdbcOperations.update("UPDATE example_table SET name=?", newName));
      assertEquals(newName, findById(1).getName());
      assertEquals(newName, findById(2).getName());
   }

   @Test
   public void update_noRows() {
      String newName = "updated";
      assertEquals(0, jdbcOperations.update("UPDATE example_table SET name=? WHERE id<1", newName));
   }

   @Test
   public void delete() {
      int id = 2;
      assertEquals("bbb", findById(id).getName());
      assertEquals(1, jdbcOperations.update("DELETE FROM example_table WHERE id=?", id));
      assertNotFound(id);
   }

   @Test
   public void badSqlGrammarException() {
      Throwable t = catchException(() -> jdbcOperations.update("Invalid SQL"));
      assertSame(BadSqlGrammarException.class, t.getClass());
      assertTrue(t.getCause() instanceof SQLException);
   }

   private ExampleBean findById(int id) {
      return jdbcOperations.queryForObject("SELECT id, name, start_date FROM example_table WHERE id=?", ROW_MAPPER, id);
   }

   private void assertException(Class<? extends Throwable> expectedClass, String expectedMessage, Runnable r) {
      Throwable actual = catchException(r);
      assertSame(expectedClass, actual.getClass());
      assertEquals(expectedMessage, actual.getMessage());
   }

   private Throwable catchException(Runnable r) {
      try {
         r.run();
         return null;
      } catch (Throwable t) {
         return t;
      }
   }

   private void assertNotFound(int id) {
      assertException(EmptyResultDataAccessException.class, "Incorrect result size: expected 1, actual 0", () -> findById(id));
   }
}
