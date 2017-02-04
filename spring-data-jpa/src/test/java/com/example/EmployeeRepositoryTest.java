package com.example;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Function;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;

public class EmployeeRepositoryTest {
   private static final int NUMBER_OF_EMPLOYEES = 10;
   private AnnotationConfigApplicationContext applicationContext;
   private EmployeeRepository employeeRepository;

   @Before
   public void setUp() {
      applicationContext = new AnnotationConfigApplicationContext("com.example");
      employeeRepository = applicationContext.getBean(EmployeeRepository.class);
   }

   @After
   public void tearDown() {
      applicationContext.close();
   }

   // test methods defined in CrudRepository

   /**
    * Returns the number of entities available.
    *
    * @see http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#count--
    */
   @Test
   public void testCount() {
      assertEquals(NUMBER_OF_EMPLOYEES, employeeRepository.count());
   }

   /**
    * Deletes the entity with the given id.
    *
    * @see http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#delete-ID-
    */
   @Test
   public void testDeleteID() {
      final int id = 1;
      assertTrue(employeeRepository.exists(id));
      employeeRepository.delete(id);
      assertFalse(employeeRepository.exists(id));
   }

   /**
    * Deletes the given entities.
    *
    * @see http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#delete-java.lang.Iterable-
    */
   @Test
   public void testDeleteIterable() {
      employeeRepository.delete(Arrays.asList(employeeRepository.findOne(1), employeeRepository.findOne(4), employeeRepository.findOne(9)));
      assertNull(employeeRepository.findOne(1));
      assertNull(employeeRepository.findOne(4));
      assertNull(employeeRepository.findOne(9));
   }

   /**
    * Deletes a given entity.
    *
    * @see http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#delete-T-
    */
   @Test
   public void testDelete() {
      final int id = 1;
      assertTrue(employeeRepository.exists(id));
      Employee e = employeeRepository.getOne(id);
      employeeRepository.delete(e);
      assertFalse(employeeRepository.exists(id));
   }

   /**
    * Deletes all entities managed by the repository.
    *
    * @see http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#deleteAll--
    */
   @Test
   public void testDeleteAll() {
      assertFalse(employeeRepository.findAll().isEmpty());
      employeeRepository.deleteAll();
      assertTrue(employeeRepository.findAll().isEmpty());
   }

   /**
    * Returns whether an entity with the given id exists.
    *
    * @see http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#exists-ID-
    */
   @Test
   public void testExists() {
      int idThatExists = 1;
      assertTrue(employeeRepository.exists(idThatExists));

      int idThatDoesNotExist = 9969;
      assertFalse(employeeRepository.exists(idThatDoesNotExist));
   }

   /**
    * Returns all instances of the type.
    *
    * @see http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#findAll--
    * @see http://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html#findAll--
    */
   @Test
   public void testFindAll() {
      List<Employee> all = employeeRepository.findAll();
      assertEquals(employeeRepository.count(), all.size());
      // TODO
   }

   /**
    * Returns all instances of the type with the given IDs.
    *
    * @see http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#findAll-java.lang.Iterable-
    * @see http://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html#findAll-java.lang.Iterable-
    */
   @Test
   public void testFindAllIterable() {
      List<Employee> all = employeeRepository.findAll(Arrays.asList(1, 4, 9));
      assertIdAndFirstName(all.get(0), 1, "Jack");
      assertIdAndFirstName(all.get(1), 4, "Olivia");
      assertIdAndFirstName(all.get(2), 9, "Jacob");
   }

   private void assertIdAndFirstName(Employee e, int expectedId, String expectedFirstName) {
      assertEquals(expectedId, e.getId());
      assertEquals(expectedFirstName, e.getFirstName());
   }

   /**
    * Retrieves an entity by its id.
    *
    * @see http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#findOne-ID-
    * @see http://stackoverflow.com/questions/24482117/when-use-getone-and-findone-methods-spring-data-jpa
    * @see #testGetOne()
    */
   @Test
   public void testFindOne() {
      Employee e = employeeRepository.findOne(1);
      assertEquals("Jack", e.getFirstName());
   }

   /**
    * Saves all given entities.
    *
    * @see http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#save-java.lang.Iterable-
    */
   @Test
   public void testSaveIterable() {
      // TODO
   }

   /**
    * Saves a given entity.
    *
    * @see http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#save-S-
    * @see #testSaveAndFlush()
    */
   @Test
   public void testSave() {
      ExampleService s = applicationContext.getBean(ExampleService.class);
      Employee b = new Employee();
      b.setId(1);
      b.setFirstName("012345678901234567890123456789012345678901234567891");
      b.setStartDate(new Date());
      try {
         s.required(() -> {
            Employee e2 = employeeRepository.save(b);
            employeeRepository.flush();
            fail("xya");
         });
      } catch (DataIntegrityViolationException e) {

      }

      try {
         s.required(() -> {
            Employee e2 = employeeRepository.save(b);
            // exampleRepository.flush();
            fail("xyz");
         });
      } catch (AssertionError e) {
         assertEquals("xyz", e.getMessage());
      }
      // assertEquals(3, exampleRepository.count());
      // s.requiresNew(() -> assertEquals(2, exampleRepository.count()));
      // // assertEquals(878, exampleRepository.count());

      // TODO test save throwing exception due to null constraint of primary key constraint
      // TODO how to test not calling flush()?
   }

   // test methods defined in PagingAndSortingRepository

   /**
    * Returns a Page of entities meeting the paging restriction provided in the Pageable object.
    *
    * @see http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
    */
   @Test
   public void testFindAllPageable() {
      // TODO
   }

   /**
    * Returns all entities sorted by the given options.
    *
    * @see http://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html
    */
   @Test
   public void testFindAllSort() {
      Sort sort = new Sort(Sort.Direction.ASC, "firstName");
      List<Employee> sortedEmployees = employeeRepository.findAll(sort);
      Object[] sortedFirstNames = sortedEmployees.stream().map(Employee::getFirstName).toArray();
      assertArrayEquals(new Object[] { "Amelia", "Emily", "Isla", "Jack", "Jacob", "James", "Olivia", "Poppy", "Thomas", "William" }, sortedFirstNames);
   }

   // test methods defined in JpaRepository

   /**
    * Deletes the given entities in a batch which means it will create a single Query.
    * <p>
    * Assume that it will clear the EntityManager after the call.
    *
    * @see http://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html#deleteAllInBatch--
    */
   @Test
   public void testDeleteAllInBatch() {
      // TODO
   }

   /**
    * Deletes the given entities in a batch which means it will create a single Query.
    * <p>
    * Assume that it will clear the EntityManager after the call.
    *
    * @see http://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html#deleteInBatch-java.lang.Iterable-
    */
   @Test
   public void testDeleteInBatchIterable() {
      // TODO
   }

   /**
    * Flushes all pending changes to the database.
    *
    * @see http://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html#flush--
    */
   @Test
   public void testFlush() {
      // TODO
   }

   /**
    * Returns a reference to the entity with the given identifier.
    *
    * @see http://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html#getOne-ID-
    * @see http://stackoverflow.com/questions/24482117/when-use-getone-and-findone-methods-spring-data-jpa
    * @see #testFindOne()
    */
   @Test
   public void testGetOne() {
      // TODO test getOne and demonstrate difference between getOne and findOne
   }

   /**
    * Saves an entity and flushes changes instantly.
    *
    * @see http://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html#saveAndFlush-S-
    */
   @Test
   public void testSaveAndFlush() {
      // TODO
   }

   // test queries defined in ExampleRepository

   @Test
   public void testByFirstName() {
      Employee e = employeeRepository.findByFirstName("Jack");
      assertEquals("Jack", e.getFirstName());
   }

   /**
    * Example of "between" and "order by"
    */
   @Test
   public void testQueryForList() {
      List<Employee> e = employeeRepository.findByStartDateBetween(new Date(0), new Date());
      assertEquals(NUMBER_OF_EMPLOYEES, e.size());
      // TODO come up with better test
   }

   @Test
   public void testQueryForList2() {
      List<Employee> e = employeeRepository.findByLastNameLike("S_i%");
      assertContainsLastNames(e, "Smith");
   }

   private <T> void assertContainsLastNames(List<Employee> e, T... expected) {
      assertContains(e, o -> o.getLastName(), "Smith");
   }

   private <T> void assertContains(List<Employee> e, Function<Employee, T> mapper, T... expected) {
      Set<T> set = asSet(expected);
      assertEquals(expected.length, e.size());
      assertTrue(e.stream().map(mapper).allMatch(set::contains));
   }

   private <T> Set<T> asSet(T[] expected) {
      Set<T> s = new HashSet<>();
      Collections.addAll(s, expected);
      return s;
   }
}
