# Spring Cache Learning Tests

This project contains JUnit tests which provide examples of how to use the [Spring Framework](https://spring.io/) caching abstraction.

Features demonstrated by the tests include:

* Annotation-based cache configuration using `@EnableCaching`, `@Cacheable`, `@CachePut` and `@CacheEvict`.
* The `org.springframework.cache.concurrent.ConcurrentMapCacheManager` cache manager.

Features _not_ currently covered by these tests include:

* Customising the cache key using the `key` attributes of `@Cacheable`, `@CachePut` and `@CacheEvict`..
* Conditional caching using the `condition` and `unless` attributes of `@Cacheable` and `@CachePut`.
* The `condition`, `allEntries` and `beforeInvocation` attributes of `@CacheEvict`. 
* The  `@Caching` annotation.
* XML-based cache configuration.
* Other cache managers (e.g. `org.springframework.cache.ehcache.EhCacheCacheManager`).

---

Further reading:

* [Spring Framework Cache Abstraction](https://docs.spring.io/spring/docs/current/spring-framework-reference/html/cache.html)
