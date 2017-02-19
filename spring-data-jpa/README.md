# Spring Data JPA Learning Tests

This project contains JUnit tests which provide examples of how to use Spring Data JPA to access a database.

The tests cover:

* Implementing `org.springframework.data.jpa.repository.JpaRepository` to create repositories based on Spring and JPA.
* JavaConfig based repository configuration using `@EnableJpaRepositories`.

Features _not_ currently covered by these tests include:

* Support for Querydsl predicates and thus type-safe JPA queries.
* Transparent auditing of domain class.
* Pagination support.
* Ability to integrate custom data access code.
* Support for XML based entity mapping.

---

Further reading:

* [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)
* [Repository query keywords](http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords)
* [org.springframework.data.jpa.repository.JpaRepository Javadoc](http://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
