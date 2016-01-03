package com.how2examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Tests of the life-cycle of beans created by Spring's IoC container.
 *
 * @see http://docs.spring.io/spring/docs/current/spring-framework-reference/html/beans.html
 */
public class BeanLifecycleTest {
   /**
    * The central interface within a Spring application.
    *
    * @see https://spring.io/understanding/application-context
    */
   private AnnotationConfigApplicationContext context;

   /**
    * Creates the application context, scanning for bean definitions in the given package.
    */
   @Before
   public void createContext() {
      context = new AnnotationConfigApplicationContext("com.how2examples");
   }

   /**
    * Close the application context, destroying all beans in its bean factory.
    */
   @After
   public void closeContext() {
      context.close();
   }

   /**
    * Example of obtaining a bean which has been annotated with a singleton scope.
    * <p>
    * Tests that consecutive calls to {@code BeanFactory.getBean(String)} return the same instance of the requested bean.
    */
   @Test
   public void testSingleton() {
      SimpleSingleton singleton1 = context.getBean(SimpleSingleton.class);
      SimpleSingleton singleton2 = context.getBean(SimpleSingleton.class);
      assertSame(singleton1, singleton2);

      Object state = new Object();
      singleton1.setState(state);
      assertSame(state, singleton2.getState());
   }

   /**
    * Example of obtaining a bean which has been annotated with a prototype scope.
    * <p>
    * Tests that consecutive calls to {@code BeanFactory.getBean(String)} return different instances of the requested bean.
    */
   @Test
   public void testPrototype() {
      SimplePrototype prototype1 = context.getBean(SimplePrototype.class);
      SimplePrototype prototype2 = context.getBean(SimplePrototype.class);
      assertNotSame(prototype1, prototype2);

      Object state1 = new Object();
      Object state2 = new Object();
      prototype1.setState(state1);
      prototype2.setState(state2);
      assertSame(state1, prototype1.getState());
      assertSame(state2, prototype2.getState());
   }

   /**
    * Example of obtaining a POJO using {@code BeanFactory.getBean(Class)}.
    * <p>
    * The POJO - {@code PlainBean} - does not include any Spring annotations but has instead been configured using Spring JavaConfig.
    */
   @Test
   public void testPlainBeanByClass() {
      PlainBean b = context.getBean(PlainBean.class);
      context.close();
      assertSteps(b.getRecorder(), "PlainBean()", "setSimplePrototype(SimplePrototype)", "init()", "shutdown()");
   }

   /**
    * Example of obtaining a POJO using both {@code BeanFactory.getBean(String)} and {@code BeanFactory.getBean(Class)}.
    * <p>
    * The POJO - {@code PlainBean} - does not include any Spring annotations but has instead been configured using Spring JavaConfig.
    */
   @Test
   public void testPlainBeanByName() {
      PlainBean b = (PlainBean) context.getBean("nameSpecifiedByAnnotation");
      assertSame(context.getBean(PlainBean.class), b);
      context.close();
      assertSteps(b.getRecorder(), "PlainBean()", "setSimplePrototype(SimplePrototype)", "init()", "shutdown()");
   }

   /**
    * Example of singleton scope bean life-cycle events notifications using annotations.
    * <p>
    * The requested bean - {@code AutowiredSingletonBean} - includes a {@code PostConstruct} annotated method that will be called once all properties have been
    * set. The bean also has a {@code PreDestroy} annotated method that will be called to indicate that the bean is about to be removed from the container.
    */
   @Test
   public void testAutowiredSingletonBean() {
      AutowiredSingletonBean b = context.getBean(AutowiredSingletonBean.class);
      context.close();
      assertSteps(b.getRecorder(), "AutowiredSingletonBean()", "setSimplePrototype(SimplePrototype)", "@PostConstruct", "@PreDestroy");
   }

   /**
    * Example of prototype scope bean life-cycle events notifications using annotations.
    * <p>
    * The requested bean - {@code AutowiredPrototypeBean} - includes a {@code PostConstruct} annotated method that will be called once all properties have been
    * set. The bean also has a {@code PreDestroy} annotated method but, unlike with singleton scope beans, this will <i>not</i> be automatically called by the
    * container. From Spring documentation: <blockquote>"In contrast to the other scopes, Spring does not manage the complete lifecycle of a prototype bean: the
    * container instantiates, configures, and otherwise assembles a prototype object, and hands it to the client, with no further record of that prototype
    * instance. Thus, although initialization lifecycle callback methods are called on all objects regardless of scope, in the case of prototypes, configured
    * destruction lifecycle callbacks are not called."</blockquote>
    *
    * @see http://docs.spring.io/spring/docs/current/spring-framework-reference/html/beans.html#beans-factory-scopes-prototype
    */
   @Test
   public void testAutowiredPrototypeBean() {
      AutowiredPrototypeBean b = context.getBean(AutowiredPrototypeBean.class);
      context.close();
      assertSteps(b.getRecorder(), "AutowiredPrototypeBean()", "setSimplePrototype(SimplePrototype)", "@PostConstruct");
   }

   /**
    * Example of bean life-cycle events notifications using interfaces.
    * <p>
    * The requested bean - {@code AwareBean} - implements {@code ApplicationContextAware}, {@code BeanFactoryAware}, {@code BeanNameAware},
    * {@code DisposableBean} and {@code InitializingBean}.
    */
   @Test
   public void testAwareBean() {
      AwareBean b = context.getBean(AwareBean.class);
      context.close();
      assertSteps(b.getRecorder(), "AwareBean()", "setSimplePrototype(SimplePrototype)", "setBeanName(awareBean)", "setBeanFactory(BeanFactory)",
            "setApplicationContext(ApplicationContext)", "afterPropertiesSet()", "destroy()");
   }

   /**
    * Example of a bean being autowired using constructor injection.
    */
   @Test
   public void testConstructorInjection() {
      ConstructorInjectedBean b = context.getBean(ConstructorInjectedBean.class);
      assertSame(context.getBean(AutowiredSingletonBean.class), b.getAutowiredSingletonBean());
      assertSame(context.getBean(PlainBean.class), b.getPlainBean());
   }

   /**
    * Demonstration of the lazy instantiation of beans.
    */
   @Test
   public void testLazy() {
      long now = System.nanoTime();

      // "lazyBean" is configured with a @Lazy annotation, "eagerBean is not
      CreatedTimeAwareBean lazy = (CreatedTimeAwareBean) context.getBean("lazyBean");
      CreatedTimeAwareBean eager = (CreatedTimeAwareBean) context.getBean("eagerBean");

      // assert that "lazyBean" was created after this test was invoked and "eagerBean" was created before this test was invoked
      assertTrue(now < lazy.getCreatedTime());
      assertTrue(now > eager.getCreatedTime());
   }

   /**
    * Example of an exception being thrown when attempting to use {@code BeanFactory.getBean(Class)} to retrieve a bean that has been defined more than once.
    */
   @Test
   public void testNoUniqueBeanDefinitionException() {
      try {
         context.getBean(CreatedTimeAwareBean.class);
         fail();
      } catch (NoUniqueBeanDefinitionException e) {
         assertEquals(
               "No qualifying bean of type [com.how2examples.CreatedTimeAwareBean] is defined: expected single matching bean but found 2: lazyBean,eagerBean",
               e.getMessage());
      }
   }

   /**
    * Example of an exception being thrown when attempting to use {@code BeanFactory.getBean(Class)} to retrieve a bean that has not been defined.
    */
   @Test
   public void testNoSuchBeanDefinitionException() {
      try {
         context.getBean(StringBuilder.class);
         fail();
      } catch (NoSuchBeanDefinitionException e) {
         assertEquals("No qualifying bean of type [java.lang.StringBuilder] is defined", e.getMessage());
      }
   }

   private void assertSteps(LifeCycleRecorder r, String... steps) {
      assertEquals(String.join("|", steps), r.toString());
   }
}
