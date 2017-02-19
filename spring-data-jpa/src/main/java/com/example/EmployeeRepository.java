package com.example;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {
   @Modifying
   @Query("update Employee set firstName=?2 where id=?1")
   int updatePriceByName(int id, String name);

   @Query("select e from Employee e where e.id=?1")
   Employee selectXyz(int id);

   Employee findByFirstName(String firstName);

   List<Employee> findByLastName(String lastName);

   List<Employee> findByLastNameNot(String lastName);

   List<Employee> findByLastNameIgnoreCase(String lastName);

   List<Employee> findByLastNameStartsWith(String lastName);

   List<Employee> findByLastNameEndsWith(String lastName);

   List<Employee> findByLastNameLike(String lastName);

   List<Employee> findByLastNameNotLike(String lastName);

   List<Employee> findByLastNameIn(List<String> x);

   List<Employee> findByLastNameNotIn(List<String> x);

   List<Employee> findByStartDateBefore(Date date);

   List<Employee> findByStartDateAfter(Date date);

   List<Employee> findByStartDateBetween(Date start, Date end);

   List<Employee> findByFirstNameAndLastName(String firstName, String lastName);

   List<Employee> findByFirstNameAndLastNameAllIgnoreCase(String firstName, String lastName);

   List<Employee> findByFirstNameOrLastName(String firstName, String lastName);

   int countByEndDateNotNull();
}
