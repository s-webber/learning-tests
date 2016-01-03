package com.how2examples;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/** Example of a component that implements interfaces to receive notifications of its life-cycle. */
@Component
@Scope("singleton")
public class AwareBean implements ApplicationContextAware, BeanFactoryAware, BeanNameAware, DisposableBean, InitializingBean {
   private final LifeCycleRecorder recorder = new LifeCycleRecorder();

   public AwareBean() {
      recorder.record("AwareBean()");
   }

   @Autowired
   public void setSimplePrototype(SimplePrototype simplePrototype) {
      recorder.record("setSimplePrototype(SimplePrototype)");
   }

   @Override
   public void setBeanName(String beanName) {
      recorder.record("setBeanName(" + beanName + ")");
   }

   @Override
   public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
      recorder.record("setBeanFactory(BeanFactory)");
   }

   @Override
   public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
      recorder.record("setApplicationContext(ApplicationContext)");
   }

   @Override
   public void afterPropertiesSet() throws Exception {
      recorder.record("afterPropertiesSet()");
   }

   @Override
   public void destroy() throws Exception {
      recorder.record("destroy()");
   }

   public LifeCycleRecorder getRecorder() {
      return recorder;
   }
}
