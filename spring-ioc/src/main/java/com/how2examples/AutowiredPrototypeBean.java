package com.how2examples;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** Example of a prototype-scoped component autowired using annotations. */
@Component
@Scope("prototype")
public class AutowiredPrototypeBean {
   private final LifeCycleRecorder recorder = new LifeCycleRecorder();

   public AutowiredPrototypeBean() {
      recorder.record("AutowiredPrototypeBean()");
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
