# Example of using lru_cache - a decorator to wrap a function with a memoizing callable that saves up to the maxsize most recent calls.
# It can save time when an expensive or I/O bound function is periodically called with the same arguments.
# See: https://docs.python.org/3/library/functools.html
from functools import lru_cache

invocation_ctr = 0

@lru_cache(maxsize=3)
def function_with_lru_cache(x):
    global invocation_ctr
    invocation_ctr += 1
    return invocation_ctr


def test_lru_cache():
    assert 1 == function_with_lru_cache('a') # adds 'a' to cache
    assert 2 == function_with_lru_cache('b') # adds 'b' to cache
    assert 3 == function_with_lru_cache('c') # adds 'c' to cache, have now reached maxsize limit
    assert 1 == function_with_lru_cache('a') # retrieves cached value for 'a', making it the most recently used
    assert 4 == function_with_lru_cache('d') # causes 'b' to be removed, as least recently used and have reached maxsize limit
    assert 5 == function_with_lru_cache('b') # causes 'c' to be removed
    assert 1 == function_with_lru_cache('a') # retrieves cached value for 'a'
    assert 6 == function_with_lru_cache('c') # causes 'd' to be removed
    assert 5 == function_with_lru_cache('b') # retrieves cached value for 'b'
    assert 7 == function_with_lru_cache('d') # causes 'a' to be removed
    assert 8 == function_with_lru_cache('a') # causes 'c' to be removed
