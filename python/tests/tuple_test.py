import pytest


def test_type():
    assert isinstance((), tuple)
    # If parentheses contain a single object then the parentheses are treated as redundant.
    # To create a tuple with a single object then the element must be followed by a comma.
    assert isinstance(("a"), str)
    assert isinstance(("a",), tuple)
    assert isinstance(("a", "b", "c"), tuple)


def test_len():
    assert len(()) == 0
    assert len(("a",)) == 1
    assert len(("a", "b", "a", "a", "c", "b", "a")) == 7


def test_access():
    x = ("a", "b", "c")
    assert x[0] == "a"
    assert x[1] == "b"
    assert x[2] == "c"


def test_access_out_of_range():
    x = ("a", "b", "c")
    with pytest.raises(IndexError) as e:
        x[3]
    assert "tuple index out of range" in str(e.value)


def test_cannot_set():
    x = ("a", "b", "c")
    with pytest.raises(TypeError) as e:
        x[1] = "z"
    assert "'tuple' object does not support item assignment" in str(e.value)


def test_count():
    x = ("a", "b", "a", "a", "c", "b", "a")
    assert x.count("a") == 4
    assert x.count("b") == 2
    assert x.count("c") == 1
    assert x.count("z") == 0


def test_index():
    x = ("a", "b", "a", "a", "c", "b", "a")
    assert x.index("a") == 0
    assert x.index("a", 3) == 3
    assert x.index("a", 4) == 6


def test_index_not_in_tuple():
    x = ("a", "b", "c")
    with pytest.raises(ValueError) as e:
        x.index("z")
    assert "tuple.index(x): x not in tuple" in str(e.value)
