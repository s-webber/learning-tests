package com.how2examples;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.atMost;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.only;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Observable;
import java.util.Observer;
import java.util.Random;
import java.util.function.Supplier;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.exceptions.base.MockitoAssertionError;
import org.mockito.exceptions.verification.NeverWantedButInvoked;
import org.mockito.exceptions.verification.NoInteractionsWanted;
import org.mockito.exceptions.verification.TooLittleActualInvocations;
import org.mockito.exceptions.verification.TooManyActualInvocations;
import org.mockito.exceptions.verification.WantedButNotInvoked;

/**
 * Examples of using static methods of {@code org.mockito.Mockito} to use mock objects in JUnit tests.
 *
 * @see http://site.mockito.org/mockito/docs/current/org/mockito/Mockito.html
 */
public class MockitoTest {
   /** Example of how {@code Mockito.verify(T)} can be used to assert that a certain behaviour has happened exactly once. */
   @Test
   public void testVerify() {
      CharSequence mock = mock(CharSequence.class);
      try {
         verify(mock).length();
         fail();
      } catch (WantedButNotInvoked e) {
         // expected as length() has not yet been invoked
      }

      mock.length();
      verify(mock).length();

      mock.length();

      try {
         verify(mock).length();
         fail();
      } catch (TooManyActualInvocations e) {
         // expected as length() has been invoked more than once
      }
   }

   /** Examples of how {@code Mockito.verify(T, VerificationMode)} can be used to assert that a certain behaviour has happened a specified number of times. */
   @Test
   public void testVerifyTimes() {
      CharSequence mock = mock(CharSequence.class);

      try {
         verify(mock, times(3)).length();
         fail();
      } catch (WantedButNotInvoked e) {
         // expected as length() has not been invoked at all
      }

      mock.length();
      mock.length();
      mock.length();

      verify(mock, times(3)).length();

      try {
         verify(mock, times(2)).length();
         fail();
      } catch (TooManyActualInvocations e) {
         // expected as length() has been invoked 3, rather than 2, times
      }

      try {
         verify(mock, times(4)).length();
         fail();
      } catch (TooLittleActualInvocations e) {
         // expected as length() has been invoked 3, rather than 4, times
      }
   }

   /** Examples of how {@code Mockito.never()} can be used to assert that a certain behaviour has not happened. */
   @Test
   public void testNever() {
      CharSequence mock = mock(CharSequence.class);

      mock.charAt(5);

      verify(mock, never()).charAt(7);

      mock.charAt(7);

      try {
         verify(mock, never()).charAt(7);
         fail();
      } catch (NeverWantedButInvoked e) {
         // expected as charAt(7) <i>has</i> now been invoked
      }
   }

   /** Examples of how {@code Mockito.never()} can be used to assert that a certain behaviour was the only behaviour that was invoked. */
   @Test
   public void testOnly() {
      CharSequence mock = mock(CharSequence.class);

      try {
         verify(mock, only()).length();
      } catch (WantedButNotInvoked e) {
         // expected as length() has not been invoked
      }

      mock.length();
      verify(mock, only()).length();

      mock.chars();

      try {
         verify(mock, only()).length();
         fail();
      } catch (NoInteractionsWanted e) {
         // expected as length() is <i>not</i> the only method to be invoked - chars() has also been invoked
      }
   }

   /** Examples of how {@code Mockito.atMost(int)} can be used to assert that a certain behaviour has not happened more than a specified number of times. */
   @Test
   public void testAtMost() {
      Observable mock = mock(Observable.class);
      mock.notifyObservers();
      mock.notifyObservers();
      mock.notifyObservers();

      verify(mock, atMost(3)).notifyObservers();
      verify(mock, atMost(4)).notifyObservers();

      try {
         verify(mock, atMost(2)).notifyObservers();
         fail();
      } catch (MockitoAssertionError e) {
         assertEquals("Wanted at most 2 times but was 3", e.getMessage().trim());
      }
   }

   /** Examples of how {@code Mockito.atLeast(int)} can be used to assert that a certain behaviour has not happened less than a specified number of times. */
   @Test
   public void testAtLeast() {
      Observable mock = mock(Observable.class);
      mock.notifyObservers();
      mock.notifyObservers();
      mock.notifyObservers();

      verify(mock, atLeast(3)).notifyObservers();
      verify(mock, atLeast(2)).notifyObservers();

      try {
         verify(mock, atLeast(4)).notifyObservers();
         fail();
      } catch (TooLittleActualInvocations e) {
         assertTrue(e.getMessage().contains("Wanted *at least* 4 times"));
      }
   }

   /** Uses {@code thenReturn} to specify the value to be returned when a method is called on a mock object. */
   @Test
   public void testThenReturn() {
      Object expected = new Object();
      Supplier<Object> mock = when(mock(Supplier.class).get()).thenReturn(expected).getMock();

      assertEquals(expected, mock.get());
   }

   /** Uses {@code thenReturn} to specify values to be returned when a method is called on a mock object. */
   @Test
   public void testThenReturnConsecutiveCalls() {
      Random mock = when(mock(Random.class).nextInt()).thenReturn(9, -7, 42).getMock();

      assertEquals(9, mock.nextInt());
      assertEquals(-7, mock.nextInt());
      assertEquals(42, mock.nextInt());
      assertEquals(42, mock.nextInt());
      assertEquals(42, mock.nextInt());
   }

   /** Uses {@code thenThrow} to specify the exception to be thrown when a method is called on a mock object. */
   @Test
   public void testThenThrow() {
      IllegalStateException toBeThrown = new IllegalStateException();
      Supplier<Object> mock = when(mock(Supplier.class).get()).thenThrow(toBeThrown).getMock();

      try {
         mock.get();
         fail();
      } catch (IllegalStateException e) {
         assertSame(toBeThrown, e);
      }
   }

   /** Uses {@code thenThrow} to specify the exception to be thrown when a method with a {@code void} return type is called on a mock object. */
   @Test
   public void testVoidMethodDoThrow() {
      Runnable mock = mock(Runnable.class);
      IllegalStateException toBeThrown = new IllegalStateException();
      doThrow(toBeThrown).when(mock).run();

      try {
         mock.run();
         fail();
      } catch (IllegalStateException e) {
         assertSame(toBeThrown, e);
      }
   }

   /** Uses an {@code ArgumentCaptor} to capture the arguments passed to a mocked method. */
   @Test
   public void testCaptureArguments() {
      ArgumentCaptor<Observable> observableCaptor = ArgumentCaptor.forClass(Observable.class);
      ArgumentCaptor<Object> objectCaptor = ArgumentCaptor.forClass(Object.class);
      Observer mock = mock(Observer.class);
      Object object = new Object();
      Observable observable = new Observable();

      mock.update(observable, object);

      verify(mock).update(observableCaptor.capture(), objectCaptor.capture());
      assertSame(observable, observableCaptor.getValue());
      assertSame(object, objectCaptor.getValue());
   }

   /** Uses {@code Mockito.verifyNoMoreInteractions(Object...)} to verify that no unverified interactions have occurred on the specified mocks. */
   @Test
   public void testVerifyNoMoreInteractions() {
      CharSequence mock = mock(CharSequence.class);

      mock.charAt(1);
      mock.charAt(2);
      mock.charAt(3);

      verify(mock).charAt(1);
      verify(mock).charAt(2);
      verify(mock).charAt(3);

      verifyNoMoreInteractions(mock);

      mock.charAt(4);

      try {
         verifyNoMoreInteractions(mock);
         fail();
      } catch (NoInteractionsWanted e) {
         // expected as charAt(4) has been invoked but a corresponding call to verify(T)
      }
   }

   /** Uses {@code Mockito.verifyZeroInteractions(Object...)} to verify that no interactions have occurred on the specified mocks. */
   @Test
   public void testVerifyZeroInteractions() {
      CharSequence mock = mock(CharSequence.class);

      verifyZeroInteractions(mock);

      mock.length();

      try {
         verifyZeroInteractions(mock);
         fail();
      } catch (NoInteractionsWanted e) {
         // expected as length() has been invoked on the mock object
      }
   }
}
