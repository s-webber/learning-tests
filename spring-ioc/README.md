# Spring Inversion of Control (IoC) Learning Tests

This project contains JUnit tests which provide examples of how the [Spring](https://spring.io/) container creates, configures and manages beans.

Features demonstrated by the tests include:

* Java-based configuration using `@Configuration`-annotated classes and `@Bean`-annotated methods.
* Annotation-based configuration using `@Component`-annotated classes and `@Autowired`-annotated methods.
* Notification of life-cycle events using `ApplicationContextAware`, `BeanFactoryAware`, `BeanNameAware`, `DisposableBean` and `InitializingBean` interfaces.
* Notification of life-cycle events using `@PostConstruct` and `@PreDestroy` annotations.
* Configuration of singleton and prototype bean scopes using the `@Scope` annotation.
* Configuration of lazy instantiation using the `@Lazy` annotation.
* Autowiring bean properties by type.

Features _not_ currently covered by these tests include:

* [XML-based](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/beans.html#beans-factory-metadata) configuration.
* The request, session, global session and application [bean scopes](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/beans.html#beans-factory-scopes).
* The full range of approaches to [autowiring](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/beans.html#beans-factory-autowire).
* The [`@DependsOn`](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/DependsOn.html) and [`@Primary`](http://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/context/annotation/Primary.html) annotations.
* The [`BeanFactoryPostProcessor`](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanFactoryPostProcessor.html) and [BeanPostProcessor](https://docs.spring.io/spring/docs/current/javadoc-api/org/springframework/beans/factory/config/BeanPostProcessor.html) interfaces.

---

Further reading:

* [The IoC container](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/beans.html)
