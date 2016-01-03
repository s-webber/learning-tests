# Mockito Learning Tests

This project contains JUnit tests which provide examples of how to use the [Mockito](http://mockito.org/) mocking framework.

Features demonstrated by the tests include:

* The verification of interactions.
* The stubbing of method calls.
* The capture of arguments to methods.
* The use of `MockitoJUnitRunner` to instantiate annotated member variables.
* The use of `@InjectMocks` to identify a member variable on which the injection of mock objects should be performed.
* The use of `BDDMockito` to construct behaviour-driven development (BDD) style tests.

Features _not_ currently covered by these tests include:

* Flexible argument matching, see [Matchers](http://site.mockito.org/mockito/docs/current/org/mockito/Matchers.html) and [AdditionalMatchers](http://site.mockito.org/mockito/docs/current/org/mockito/AdditionalMatchers.html).
* Verifying the order of method calls, see [InOrder](http://site.mockito.org/mockito/docs/current/org/mockito/InOrder.html).
* Spying on real objects, see [Mockito.spy(T)](http://site.mockito.org/mockito/docs/current/org/mockito/Mockito.html#spy(T)) and [@Spy](http://site.mockito.org/mockito/docs/current/org/mockito/Spy.html)

---

Further reading:

* [org.mockito.Mockito Javadoc](http://site.mockito.org/mockito/docs/current/org/mockito/Mockito.html)
* [Mockito DZone Refcard](https://dzone.com/refcardz/mockito)
