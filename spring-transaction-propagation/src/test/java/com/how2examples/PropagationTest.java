package com.how2examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.IllegalTransactionStateException;

public class PropagationTest {
   private static final String X = "X";
   private static final String Y = "Y";
   private static final String Z = "Z";
   private static final RuntimeException RUNTIME_EXCEPTION = new RuntimeException();
   private static final Exception EXCEPTION = new Exception();
   private static final MyRunnable THROW_EXCEPTION = () -> {
      throw EXCEPTION;
   };
   private static final MyRunnable THROW_RUNTIME_EXCEPTION = () -> {
      throw RUNTIME_EXCEPTION;
   };

   private AnnotationConfigApplicationContext applicationContext;
   private SimpleService s;
   private SimpleRepository simpleRepository;

   @Before
   public void setUp() {
      applicationContext = new AnnotationConfigApplicationContext("com.how2examples");
      s = applicationContext.getBean(SimpleService.class);
      simpleRepository = applicationContext.getBean(SimpleRepository.class);
   }

   @After
   public void tearDown() {
      applicationContext.close();
   }

   @Test
   public void noTransaction() {
      simpleRepository.insert(X);
      assertInserted(X);
   }

   @Test
   public void mandatory() throws Exception {
      assertIllegalTransactionState("No existing transaction found for transaction marked with propagation 'mandatory'", () -> s.mandatory(insert(X)));
   }

   @Test
   public void mandatoryInsideRequired() throws Exception {
      insideRequired(s::mandatory);
      assertInserted(X);
   }

   @Test
   public void mandatoryInsideRequiredRuntimeException() {
      throwRuntimeExceptionAfter(s::mandatory);
      assertNothingInserted();
   }

   @Test
   public void mandatoryInsideRequiredException() {
      throwExceptionAfter(s::mandatory);
      assertInserted(X, Y, Z);
   }

   @Test
   public void nested() throws Exception {
      s.nested(insert(X));
      assertInserted(X);
   }

   @Test
   public void nestedInsideRequired() throws Exception {
      insideRequired(s::nested);
      assertInserted(X);
   }

   @Test
   public void nestedInsideRequiredRuntimeException() {
      throwRuntimeExceptionAfter(s::nested);
      assertNothingInserted();
   }

   @Test
   public void nestedInsideRequiredException() {
      throwExceptionAfter(s::nested);
      assertInserted(X, Y, Z);
   }

   @Test
   public void nestedInsideRequiredCaughtRuntimeException() throws Exception {
      catchExceptionAfter(s::nested, RUNTIME_EXCEPTION);
      assertInserted(X, Z);
   }

   @Test
   public void nestedInsideRequiredCaughtException() throws Exception {
      catchExceptionAfter(s::nested, EXCEPTION);
      assertInserted(X, Y, Z);
   }

   @Test
   public void never() throws Exception {
      s.never(insert(X));
      assertInserted(X);
   }

   @Test
   public void neverInsideRequired() throws Exception {
      assertIllegalTransactionState("Existing transaction found for transaction marked with propagation 'never'", () -> s.required(() -> s.never(insert(X))));
   }

   @Test
   public void neverException() {
      try {
         s.never(insert(X), THROW_EXCEPTION);
      } catch (Exception e) {
         assertSame(EXCEPTION, e);
      }
      assertInserted(X);
   }

   @Test
   public void neverRuntimeException() {
      try {
         s.never(insert(X), THROW_RUNTIME_EXCEPTION);
      } catch (Exception e) {
         assertSame(RUNTIME_EXCEPTION, e);
      }
      assertInserted(X);
   }

   @Test
   public void notSupported() throws Exception {
      s.notSupported(insert(X));
      assertInserted(X);
   }

   @Test
   public void notSupportedInsideRequired() throws Exception {
      insideRequired(s::notSupported);
      assertInserted(X);
   }

   @Test
   public void notSupportedInsideRequiredRuntimeException() {
      throwRuntimeExceptionAfter(s::notSupported);
      assertInserted(Y);
   }

   @Test
   public void notSupportedInsideRequiredException() {
      throwExceptionAfter(s::notSupported);
      assertInserted(X, Y, Z);
   }

   @Test
   public void required() throws Exception {
      s.required(insert(X));
      assertInserted(X);
   }

   @Test
   public void requiredInsideRequired() throws Exception {
      insideRequired(s::required);
      assertInserted(X);
   }

   @Test
   public void requiredInsideRequiredRuntimeException() {
      throwRuntimeExceptionAfter(s::required);
      assertNothingInserted();
   }

   @Test
   public void requiredInsideRequiredException() {
      throwExceptionAfter(s::required);
      assertInserted(X, Y, Z);
   }

   @Test
   public void requiredInsideRequiredCaughtRuntimeException() throws Exception {
      catchExceptionAfter(s::required, RUNTIME_EXCEPTION);
      assertNothingInserted();
   }

   @Test
   public void requiredInsideRequiredCaughtException() throws Exception {
      catchExceptionAfter(s::required, EXCEPTION);
      assertInserted(X, Y, Z);
   }

   @Test
   public void requiresNew() throws Exception {
      s.requiresNew(insert(X));
      assertInserted(X);
   }

   @Test
   public void requiresNewInsideRequired() throws Exception {
      insideRequired(s::requiresNew);
      assertInserted(X);
   }

   @Test
   public void requiresNewInsideRequiredRuntimeException() {
      throwRuntimeExceptionAfter(s::requiresNew);
      assertInserted(Y);
   }

   @Test
   public void requiresNewInsideRequiredException() {
      throwExceptionAfter(s::requiresNew);
      assertInserted(X, Y, Z);
   }

   @Test
   public void requiresNewInsideRequiredCaughtRuntimeException() throws Exception {
      catchExceptionAfter(s::requiresNew, RUNTIME_EXCEPTION);
      assertInserted(X, Z);
   }

   @Test
   public void requiresNewInsideRequiredCaughtException() throws Exception {
      catchExceptionAfter(s::requiresNew, EXCEPTION);
      assertInserted(X, Y, Z);
   }

   @Test
   public void supports() throws Exception {
      s.supports(insert(X));
      assertInserted(X);
   }

   @Test
   public void supportsInsideRequired() throws Exception {
      insideRequired(s::supports);
      assertInserted(X);
   }

   @Test
   public void supportsInsideRequiredRuntimeException() {
      throwRuntimeExceptionAfter(s::supports);
      assertNothingInserted();
   }

   @Test
   public void supportsInsideRequiredException() {
      throwExceptionAfter(s::supports);
      assertInserted(X, Y, Z);
   }

   private void catchExceptionAfter(MyConsumer consumer, Exception e) {
      required(insert(X), () -> catchException(() -> consumer.accept(insert(Y), () -> {
         throw e;
      })), insert(Z));
   }

   private MyRunnable insert(String name) {
      return () -> simpleRepository.insert(name);
   }

   private void insideRequired(MyConsumer consumer) {
      required(() -> consumer.accept(insert(X)));
   }

   private void required(MyRunnable... runnables) {
      try {
         s.required(runnables);
      } catch (Exception e) {
      }
   }

   private void catchException(MyRunnable runnable) {
      try {
         runnable.run();
      } catch (Exception e) {
         // ignore
      }
   }

   private void assertIllegalTransactionState(String expectedMessage, MyRunnable r) throws Exception {
      try {
         r.run();
         fail("Expected: " + expectedMessage);
      } catch (IllegalTransactionStateException e) {
         assertEquals(expectedMessage, e.getMessage());
      }
      assertNothingInserted();
   }

   private void assertNothingInserted() {
      assertInserted();
   }

   private void assertInserted(String... expected) {
      List<String> actual = simpleRepository.findAll();
      assertEquals(actual.toString(), expected.length, actual.size());
      for (String e : expected) {
         if (!actual.contains(e)) {
            fail(actual.toString());
         }
      }
   }

   private void throwRuntimeExceptionAfter(MyConsumer consumer) {
      throwExceptionAfter(consumer, THROW_RUNTIME_EXCEPTION, RUNTIME_EXCEPTION);
   }

   private void throwExceptionAfter(MyConsumer consumer) {
      throwExceptionAfter(consumer, THROW_EXCEPTION, EXCEPTION);
   }

   private void throwExceptionAfter(MyConsumer consumer, MyRunnable finalRunnable, Exception expected) {
      try {
         s.required(insert(X), () -> consumer.accept(insert(Y)), insert(Z), finalRunnable);
         fail();
      } catch (Exception e) {
         assertSame(expected, e);
      }
   }

   /** Like {@code java.util.function.Consumer}, but takes variable numbers if arguments and allows checked exceptions to be thrown. */
   static interface MyConsumer {
      void accept(MyRunnable... runnables) throws Exception;
   }
}
