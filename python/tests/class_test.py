# See:
# https://docs.python.org/3/reference/datamodel.html
# https://docs.python.org/3/tutorial/classes.html


def test_class():
    o = ExampleClass(10, 12)
    assert o.x() == 10
    assert o.y() == 12
    assert str(o) == 'x: 10 y: 12'


# "Private" instance variables that cannot be accessed except from inside an object don’t exist in Python.
# However, there is a convention that is followed by most Python code: a name prefixed with an underscore (e.g. _spam)
# should be treated as a non-public part of the API (whether it is a function, a method or a data member).
class ExampleClass:
    # Called after the instance has been created (by __new__()), but before it is returned to the caller.
    # The arguments are those passed to the class constructor expression.
    def __init__(self, x, y):
        self._x = x
        self._y = y

    def x(self):
        return self._x

    def y(self):
        return self._y

    # Called by str(object) and the built-in functions format() and print() to compute the "informal"
    # or nicely printable string representation of an object. The return value must be a string object.
    def __str__(self):
        return "x: {0} y: {1}".format(self._x, self._y)
