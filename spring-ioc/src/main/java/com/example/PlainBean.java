package com.example;

/**
 * Example of a plain old Java object (POJO) which does not depend on any Spring classes.
 * <p>
 * Used to demonstrate how a POJO can be integrated into Spring's IoC container.
 *
 * @see Config#namedAfterMethodBean()
 */
public class PlainBean {
   private final LifeCycleRecorder recorder = new LifeCycleRecorder();

   public PlainBean() {
      recorder.record("PlainBean()");
   }

   public void setSimplePrototype(SimplePrototype simplePrototype) {
      recorder.record("setSimplePrototype(SimplePrototype)");
   }

   public void init() {
      recorder.record("init()");
   }

   public void shutdown() {
      recorder.record("shutdown()");
   }

   public LifeCycleRecorder getRecorder() {
      return recorder;
   }
}
