package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
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

public class JdbcTemplateTest {
   private static final RowMapper<Employee> ROW_MAPPER = (rs, rowNum) -> new Employee(rs.getInt("id"), rs.getString("name"), rs.getTimestamp("start_date"));

   private AnnotationConfigApplicationContext applicationContext;
   private JdbcOperations jdbcOperations; // TODO rename?

   @Before
   public void setUp() {
      applicationContext = new AnnotationConfigApplicationContext("com.example");
      jdbcOperations = applicationContext.getBean(JdbcOperations.class);
      // TODO insert data here rather than in src/test/resources/db-schema.sql script?
   }

   @After
   public void tearDown() {
      applicationContext.close();
   }

   @Test
   public void queryForList() {
      List<String> names = jdbcOperations.queryForList("SELECT name FROM employee", String.class);
      assertEquals(2, names.size());
      assertEquals("aaa", names.get(0));
      assertEquals("bbb", names.get(1));
   }

   @Test
   public void queryForList_empty() {
      List<String> names = jdbcOperations.queryForList("SELECT name FROM employee WHERE id=?", String.class, -1);
      assertTrue(names.isEmpty());
   }

   @Test
   public void query() {
      List<Employee> names = jdbcOperations.query("SELECT id, name, start_date FROM employee", ROW_MAPPER);
      assertEquals(2, names.size());
      assertEquals("aaa", names.get(0).getName());
      assertEquals("bbb", names.get(1).getName());
   }

   @Test
   public void queryForObject() {
      int count = jdbcOperations.queryForObject("SELECT COUNT(*) FROM employee", Integer.class);
      assertEquals(2, count);
   }

   @Test(expected = IncorrectResultSizeDataAccessException.class)
   public void queryForObject_moreThanOneRow() {
      jdbcOperations.queryForObject("SELECT name FROM employee", String.class);
   }

   @Test(expected = IncorrectResultSizeDataAccessException.class)
   public void queryForObject_noRow() {
      jdbcOperations.queryForObject("SELECT name FROM employee WHERE id=?", String.class, -1);
   }

   @Test
   public void queryForMap() {
      int id = 2;
      Map<String, Object> m = jdbcOperations.queryForMap("SELECT id, name, start_date FROM employee WHERE id=?", id);
      assertEquals(3, m.size());
      assertEquals(id, m.get("id"));
      assertEquals("bbb", m.get("name"));
      assertSame(Timestamp.class, m.get("start_date").getClass()); // TODO check value
   }

   @Test(expected = IncorrectResultSizeDataAccessException.class)
   public void queryForMap_moreThanOneRow() {
      jdbcOperations.queryForMap("SELECT id, name, start_date FROM employee");
   }

   @Test(expected = IncorrectResultSizeDataAccessException.class)
   public void queryForMap_noRow() {
      jdbcOperations.queryForMap("SELECT id, name, start_date FROM employee WHERE id=?", -1);
   }

   @Test
   public void queryForRowSet() {
      SqlRowSet m = jdbcOperations.queryForRowSet("SELECT id, name, start_date FROM employee");

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
      SqlRowSet m = jdbcOperations.queryForRowSet("SELECT id, name, start_date FROM employee WHERE id=?", -1);
      assertFalse(m.isBeforeFirst());
      assertFalse(m.next());
      assertFalse(m.first());
   }

   @Test
   public void queryForRowSet_InvalidResultSetAccessException() {
      SqlRowSet m = jdbcOperations.queryForRowSet("SELECT id, name, start_date FROM employee");
      String expectedMessage = "Invalid cursor position; nested exception is java.sql.SQLException: Invalid cursor position";
      assertException(InvalidResultSetAccessException.class, expectedMessage, () -> m.getString("name"));
   }

   @Test
   public void insert() {
      int id = 3;
      String name = "test";
      Timestamp startDate = new Timestamp(System.currentTimeMillis());
      assertNotFound(id);
      assertEquals(1, jdbcOperations.update("INSERT INTO employee (id, name, start_date) VALUES (?, ?, ?)", id, name, startDate));

      Employee e = findById(id);
      assertEquals(id, e.getId());
      assertEquals(name, e.getName());
      assertEquals(startDate, e.getStartDate());
   }

   @Test
   public void update_singleRow() {
      int id = 2;
      String newName = "updated";
      assertEquals("bbb", findById(id).getName());
      assertEquals(1, jdbcOperations.update("UPDATE employee SET name=? WHERE id=?", newName, id));
      assertEquals(newName, findById(id).getName());
   }

   @Test
   public void update_multipleRows() {
      String newName = "updated";
      assertEquals(2, jdbcOperations.update("UPDATE employee SET name=?", newName));
      assertEquals(newName, findById(1).getName());
      assertEquals(newName, findById(2).getName());
   }

   @Test
   public void update_noRows() {
      String newName = "updated";
      assertEquals(0, jdbcOperations.update("UPDATE employee SET name=? WHERE id<1", newName));
   }

   @Test
   public void delete() {
      int id = 2;
      assertEquals("bbb", findById(id).getName());
      assertEquals(1, jdbcOperations.update("DELETE FROM employee WHERE id=?", id));
      assertNotFound(id);
   }

   @Test
   public void badSqlGrammarException() {
      Throwable t = catchException(() -> jdbcOperations.update("Invalid SQL"));
      assertSame(BadSqlGrammarException.class, t.getClass());
      assertTrue(t.getCause() instanceof SQLException);
   }

   private Employee findById(int id) {
      return jdbcOperations.queryForObject("SELECT id, name, start_date FROM employee WHERE id=?", ROW_MAPPER, id);
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

class Employee {
   private final int id;
   private final String name;
   private final Date startDate;

   public Employee(int id, String name, Date startDate) {
      this.id = id;
      this.name = name;
      this.startDate = startDate;
   }

   public int getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public Date getStartDate() {
      return startDate;
   }
}
