# https://docs.python.org/3/library/stdtypes.html#truth-value-testing


def test_none():
    assert falsy(None)


def test_boolean():
    assert truthy(True)
    assert falsy(False)


def test_integer():
    assert falsy(0)
    assert truthy(1)
    assert truthy(2)
    assert truthy(3)
    assert truthy(999999)
    assert truthy(-1)
    assert truthy(-2)
    assert truthy(-3)
    assert truthy(-999999)


def test_float():
    assert falsy(0.0)
    assert truthy(0.1)
    assert truthy(0.2)
    assert truthy(0.3)
    assert truthy(1.0)
    assert truthy(2.0)
    assert truthy(3.14159265359)
    assert truthy(-0.1)
    assert truthy(-0.2)
    assert truthy(-0.3)
    assert truthy(-1.0)
    assert truthy(-2.0)
    assert truthy(-3.14159265359)


def test_string():
    assert falsy("")
    assert truthy("a")
    assert truthy("abc")


def test_list():
    assert falsy([])
    assert truthy("a")
    assert truthy(["a", "b", "c"])


def test_set():
    assert falsy(set())
    assert truthy({"a"})
    assert truthy({"a", "b", "c"})


def test_dictionary():
    assert falsy({})
    assert truthy({"a": 1})
    assert truthy({"a": 1, "b": 2, "c": 3})


def test_tuple():
    assert falsy(())
    assert truthy(("a",))
    assert truthy(("a", "b", "c"))


def falsy(x):
    return not truthy(x)


def truthy(x):
    if x:
        return True
    else:
        return False
