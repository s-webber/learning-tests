package com.example;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class ExampleService {
   @Cacheable("exampleCache")
   public Object cacheable(String x) {
      return new Object();
   }

   @CachePut("exampleCache")
   public Object put(String x) {
      return new Object();
   }

   @CacheEvict("exampleCache")
   public void evict(String x) {
   }

   @CacheEvict(cacheNames = "exampleCache", allEntries = true)
   public void evictAll() {
   }
}
