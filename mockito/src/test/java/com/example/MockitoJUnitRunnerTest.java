package com.example;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mockingDetails;
import static org.mockito.Mockito.verify;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Example of using {@code MockitoJUnitRunner} to instantiate annotated member variables.
 *
 * @see http://site.mockito.org/mockito/docs/current/org/mockito/runners/MockitoJUnitRunner.html
 */
@RunWith(MockitoJUnitRunner.class)
public class MockitoJUnitRunnerTest {
   /** The @Mock annotation marks this field as a mock, minimising mock creation code and aiding readability. */
   @Mock
   private List<String> mock;
   /** Captures argument values for further assertions. */
   @Captor
   ArgumentCaptor<String> argumentCaptor;

   @Test
   public void test() {
      assertNotNull(mock);
      assertTrue(mockingDetails(mock).isMock());
      mock.add("test");
      verify(mock).add(argumentCaptor.capture());
      assertEquals("test", argumentCaptor.getValue());
   }
}
