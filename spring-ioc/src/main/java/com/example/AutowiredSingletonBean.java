package com.example;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** Example of a singleton-scoped component autowired using annotations. */
@Component
@Scope("singleton")
public class AutowiredSingletonBean {
   private final LifeCycleRecorder recorder = new LifeCycleRecorder();

   public AutowiredSingletonBean() {
      recorder.record("AutowiredSingletonBean()");
   }

   @Autowired
   public void setSimplePrototype(SimplePrototype simplePrototype) {
      recorder.record("setSimplePrototype(SimplePrototype)");
   }

   @PostConstruct
   public void postConstruct() {
      recorder.record("@PostConstruct");
   }

   @PreDestroy
   public void preDestroy() {
      recorder.record("@PreDestroy");
   }

   public LifeCycleRecorder getRecorder() {
      return recorder;
   }
}
