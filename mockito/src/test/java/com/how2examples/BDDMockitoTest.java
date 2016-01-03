package com.how2examples;

import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import org.junit.Test;

/**
 * Example of using static methods of {@code org.mockito.BDDMockito} to construct tests in the Behaviour Driven Development style.
 *
 * @see http://site.mockito.org/mockito/docs/current/org/mockito/BDDMockito.html
 */
public class BDDMockitoTest {
   /** Uses {@code BDDMockito} to construct tests which follow the given / when / then Behaviour Driven Development style of writing tests. */
   @Test
   public void testBdd() throws IOException {
      // given
      Reader mockReader = mock(Reader.class);
      BufferedReader bufferedReader = new BufferedReader(mockReader);

      // when
      bufferedReader.close();

      // then
      then(mockReader).should(times(1)).close();
   }
}
