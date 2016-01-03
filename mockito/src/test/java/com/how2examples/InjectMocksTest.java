package com.how2examples;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mockingDetails;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

/**
 * Example of using {@code InjectMocks} to identify a member variable on which the injection of mock objects should be performed.
 *
 * @see http://site.mockito.org/mockito/docs/current/org/mockito/InjectMocks.html
 */
public class InjectMocksTest {
   /** The @InjectMocks annotation marks this field as an object on which the injection of mock objects should be performed. */
   @InjectMocks
   private Facade facade;
   /** The @Mock annotation marks this field as a mock, and therefore a candidate to be injected into objects annotated with InjectMocks. */
   @Mock
   private Component component;

   /** Calls {@code MockitoAnnotations.initMocks(Object)} to instantiate annotated member variables. */
   @Before
   public void initMocks() {
      MockitoAnnotations.initMocks(this);
   }

   @Test
   public void test() {
      assertNotNull(facade);
      assertNotNull(component);

      assertFalse(mockingDetails(facade).isMock());
      assertTrue(mockingDetails(component).isMock());

      assertSame(component, facade.getComponent());
   }
}

class Facade {
   private final Component component;

   Facade(Component component) {
      this.component = component;
   }

   Component getComponent() {
      return component;
   }
}

class Component {

}
