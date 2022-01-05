import inspect
import pytest

def yield_example():
    yield 'x'
    yield 'y'
    yield 'z'

def test_yield():
    g = yield_example()
    assert inspect.isgenerator(g)
    assert next(g) == 'x'
    assert next(g) == 'y'
    assert next(g) == 'z'
    with pytest.raises(StopIteration):
        next(g)

def fibonacci(n):
    a = 0
    b = 1

    for _ in range(n):
        yield a
        sum = a + b
        a = b
        b = sum

def test_fibonacci():
    assert list(fibonacci(13)) == [0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144]
