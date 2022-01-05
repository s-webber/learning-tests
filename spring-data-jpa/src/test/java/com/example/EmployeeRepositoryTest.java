package com.example;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
import org.springframework.data.domain.Sort;

// Split this class into three separate classes: CrudRepositoryTest, JpaRepository and EmployeeRepositoryTest
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
    * @see https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#deleteById-ID-
    */
   @Test
   public void testDeleteByID() {
      final int id = 1;
      assertTrue(employeeRepository.existsById(id));
      employeeRepository.deleteById(id);
      assertFalse(employeeRepository.existsById(id));
   }

   /**
    * Deletes the given entities.
    *
    * @see https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#deleteAll-java.lang.Iterable-
    */
   @Test
   public void testDeleteAllIterable() {
      employeeRepository.deleteAll(Arrays.asList(employeeRepository.findById(1).get(), employeeRepository.findById(4).get(), employeeRepository.findById(9).get()));
      assertFalse(employeeRepository.findById(1).isPresent());
      assertFalse(employeeRepository.findById(4).isPresent());
      assertFalse(employeeRepository.findById(9).isPresent());
   }

   /**
    * Deletes a given entity.
    *
    * @see https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#delete-T-
    */
   @Test
   public void testDelete() {
      final int id = 1;
      assertTrue(employeeRepository.existsById(id));
      Employee e = employeeRepository.getById(id);
      employeeRepository.delete(e);
      assertFalse(employeeRepository.existsById(id));
   }

   /**
    * Deletes all entities managed by the repository.
    *
    * @see https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#deleteAll--
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
    * @see https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#existsById-ID--
    */
   @Test
   public void testExistsById() {
      int idThatExists = 1;
      assertTrue(employeeRepository.existsById(idThatExists));

      int idThatDoesNotExist = 9969;
      assertFalse(employeeRepository.existsById(idThatDoesNotExist));
   }

   /**
    * Returns all instances of the type.
    *
    * @see https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#findAll--
    */
   @Test
   public void testFindAll() {
      List<Employee> all = employeeRepository.findAll();
      assertEquals(employeeRepository.count(), all.size());
      // TODO confirm contents of "all" rather than just confirming its size
   }

   /**
    * Returns all instances of the type with the given IDs.
    *
    * @see https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#findAllById-java.lang.Iterable-
    */
   @Test
   public void testFindAllByIdIterable() {
      List<Employee> all = employeeRepository.findAllById(Arrays.asList(1, 4, 9));
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
    * @see https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/CrudRepository.html#findById-ID-
    */
   @Test
   public void testFindById() {
      Employee e = employeeRepository.findById(1).get();
      assertEquals("Jack", e.getFirstName());
   }

   /**
    * Returns all entities sorted by the given options.
    *
    * @see https://docs.spring.io/spring-data/commons/docs/current/api/org/springframework/data/repository/PagingAndSortingRepository.html#findAll-org.springframework.data.domain.Sort-
    */
   @Test
   public void testFindAllSort() {
      Sort sort = Sort.by(new Sort.Order(Sort.Direction.ASC, "firstName"));
      List<Employee> sortedEmployees = employeeRepository.findAll(sort);
      Object[] sortedFirstNames = sortedEmployees.stream().map(Employee::getFirstName).toArray();
      assertArrayEquals(new Object[] {"Amelia", "Emily", "Isla", "Jack", "Jacob", "James", "Olivia", "Poppy", "Thomas", "William"}, sortedFirstNames);
   }

   // TODO test methods defined in JpaRepository

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
   public void testFindByStartDateBetween() {
      List<Employee> e = employeeRepository.findByStartDateBetween(new Date(0), new Date());
      assertEquals(NUMBER_OF_EMPLOYEES, e.size());
      // TODO confirm contents of "e" rather than just confirming its size
   }

   @Test
   public void testFindByLastNameLike() {
      List<Employee> e = employeeRepository.findByLastNameLike("S_i%");
      assertContainsLastNames(e, "Smith");
   }

   // TODO add tests for all methods defined on the ExampleRepository interface

   @SuppressWarnings("unchecked")
   private <T> void assertContainsLastNames(List<Employee> e, T... expected) {
      assertContains(e, o -> o.getLastName(), expected);
   }

   @SuppressWarnings("unchecked")
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
