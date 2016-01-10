# Spring Data JPA Learning Tests

This project contains JUnit tests which provide examples of how to use Spring Data JPA to access a database.

This project is still being developed. The plan is to provide tests which demonstrate:

* Sophisticated support to build repositories based on Spring and JPA
* Support for Querydsl predicates and thus type-safe JPA queries
* Transparent auditing of domain class
* Pagination support, dynamic query execution, ability to integrate custom data access code
* Validation of @Query annotated queries at bootstrap time
* Support for XML based entity mapping
* JavaConfig based repository configuration by introducing @EnableJpaRepositories.

---

Further reading:

* [Spring Data JPA](http://projects.spring.io/spring-data-jpa/)
* [Repository query keywords](http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#repository-query-keywords)
* [org.springframework.data.jpa.repository.JpaRepository Javadoc](http://docs.spring.io/spring-data/jpa/docs/current/api/org/springframework/data/jpa/repository/JpaRepository.html)
