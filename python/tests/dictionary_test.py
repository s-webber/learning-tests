import pytest


def test_type():
    assert isinstance({}, dict)


def test_get():
    x = {"a": 9}
    assert x.get("a") == 9
    assert x.get("a", 42) == 9
    assert x["a"] == 9
    assert x.get("b") is None
    assert x.get("b", 42) == 42
    with pytest.raises(KeyError):
        x["b"]


def test_keys():
    x = {"Australia": "AUD", "Malta": "EUR", "Spain": "EUR", "United Kingdom": "GBP"}
    assert list(x.keys()) == ["Australia", "Malta", "Spain", "United Kingdom"]


def test_values():
    x = {"Australia": "AUD", "Malta": "EUR", "Spain": "EUR", "United Kingdom": "GBP"}
    assert list(x.values()) == ["AUD", "EUR", "EUR", "GBP"]


def test_items():
    x = {"Australia": "AUD", "Malta": "EUR", "Spain": "EUR", "United Kingdom": "GBP"}
    assert list(x.items()) == [("Australia", "AUD"), ("Malta", "EUR"), ("Spain", "EUR"), ("United Kingdom", "GBP")]


def test_copy():
    x = {"a": 9, "b": 42, "c": 7}
    y = x.copy()
    y["d"] = 12
    assert x == {"a": 9, "b": 42, "c": 7}
    assert y == {"a": 9, "b": 42, "c": 7, "d": 12}


def test_pop():
    x = {"a": 9, "b": 42, "c": 7}
    assert x.pop("b") == 42
    assert x == {"a": 9, "c": 7}


def test_pop_unknown_key():
    x = {"a": 9, "b": 42, "c": 7}
    with pytest.raises(KeyError):
        x.pop("z")


def test_popitem():
    x = {"a": 9, "b": 42, "c": 7}
    assert x.popitem() == ("c", 7)
    assert x == {"a": 9, "b": 42}
    assert x.popitem() == ("b", 42)
    assert x == {"a": 9}
    assert x.popitem() == ("a", 9)
    assert x == {}
    with pytest.raises(KeyError):
        x.popitem()


def test_setdefault():
    x = {"b": 42}
    assert x.setdefault("b", 7) == 42
    assert x.setdefault("z", 7) == 7
    assert x.setdefault("z", 1) == 7
    assert x == {"b": 42, "z": 7}


def test_update_with_dictionary():
    x = {"a": 9, "b": 42, "c": 7}
    x.update({"b": 52, "z": 8})
    assert x == {"a": 9, "b": 52, "c": 7, "z": 8}


def test_update_with_keyword():
    x = {"a": 9, "b": 42, "c": 7}
    x.update(b=52, z=8)
    assert x == {"a": 9, "b": 52, "c": 7, "z": 8}


def test_clear():
    x = {"a": 9, "b": 42, "c": 7}
    assert len(x) == 3
    x.clear()
    assert len(x) == 0


def test_fromkeys():
    dict.fromkeys("hello") == {"h": None, "e": None, "l": None, "o": None}


def test_fromkeys_with_default():
    dict.fromkeys(["apple", "banana", "orange"], 42) == {"apple": 42, "banana": 42, "orange": 42}
