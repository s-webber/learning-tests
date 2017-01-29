# Spring Transaction Propagation Learning Tests

This project contains JUnit tests which provide examples of the transaction propagation options provided by the [Spring](https://spring.io/) framework.

Features demonstrated by the tests include:

* Using an annotation-based approach to transaction configuration.
* The different transaction propagation behaviours (`MANDATORY`, `NESTED`, `NEVER`, `NOT_SUPPORTED`, `REQUIRED`, `REQUIRES_NEW`, and `SUPPORTS`).

---

Further reading:

* [Understanding the Spring Framework's declarative transaction implementation](http://docs.spring.io/spring/docs/4.3.6.RELEASE/spring-framework-reference/html/transaction.html#tx-decl-explained)
* [Using `@Transactional`](http://docs.spring.io/spring/docs/4.3.6.RELEASE/spring-framework-reference/html/transaction.html#transaction-declarative-annotations)
* [`EnableTransactionManagement` JavaDoc](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/transaction/annotation/EnableTransactionManagement.html)
* [`Propagation` Javadoc](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/transaction/annotation/Propagation.html])
* [Data Access Object (DAO) support in Spring](http://docs.spring.io/spring-framework/docs/current/spring-framework-reference/html/dao.html)
* [`PROPAGATION_NESTED` versus `PROPAGATION_REQUIRES_NEW`](http://forum.spring.io/forum/spring-projects/data/7372-propagation-nested-versus-propagation-requires-new)
