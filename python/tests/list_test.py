# TODO min, max, del and list comprehensions
import pytest


def test_type():
    assert isinstance([], list)


def test_len():
    assert len([]) == 0
    assert len(["a"]) == 1
    assert len(["a", "b", "c"]) == 3


def test_get():
    x = ["a", "b", "c"]
    assert x[0] == "a"
    assert x[1] == "b"
    assert x[2] == "c"
    assert x[-1] == "c"
    assert x[-2] == "b"
    assert x[-3] == "a"


def test_get_index_out_of_range():
    x = ["a", "b", "c"]
    with pytest.raises(IndexError):
        x[3]
    with pytest.raises(IndexError) as e:
        x[-4]
    assert "list index out of range" in str(e.value)


def test_update():
    x = ["a", "b", "c"]
    x[0] = "apple"
    assert x == ["apple", "b", "c"]
    x[1] = "banana"
    assert x == ["apple", "banana", "c"]
    x[2] = "lemon"
    assert x == ["apple", "banana", "lemon"]
    x[-1] = "kiwi"
    assert x == ["apple", "banana", "kiwi"]
    x[-2] = "orange"
    assert x == ["apple", "orange", "kiwi"]
    x[-3] = "pear"
    assert x == ["pear", "orange", "kiwi"]


def test_update_out_of_range():
    x = ["a", "b", "c"]
    with pytest.raises(IndexError):
        x[3] = "banana"
    with pytest.raises(IndexError) as e:
        x[-4] = "banana"
    assert "list assignment index out of range" in str(e.value)


def test_split_positive_start():
    x = ["a", "b", "c", "d", "e"]
    assert x[0:] == ["a", "b", "c", "d", "e"]
    assert x[1:] == ["b", "c", "d", "e"]
    assert x[2:] == ["c", "d", "e"]
    assert x[3:] == ["d", "e"]
    assert x[4:] == ["e"]
    assert x[5:] == []
    assert x[999:] == []


def test_split_positive_stop():
    x = ["a", "b", "c", "d", "e"]
    assert x[:0] == []
    assert x[:1] == ["a"]
    assert x[:2] == ["a", "b"]
    assert x[:3] == ["a", "b", "c"]
    assert x[:4] == ["a", "b", "c", "d"]
    assert x[:5] == ["a", "b", "c", "d", "e"]
    assert x[:999] == ["a", "b", "c", "d", "e"]


def test_split_negative_start():
    x = ["a", "b", "c", "d", "e"]
    assert x[-1:] == ["e"]
    assert x[-2:] == ["d", "e"]
    assert x[-3:] == ["c", "d", "e"]
    assert x[-4:] == ["b", "c", "d", "e"]
    assert x[-5:] == ["a", "b", "c", "d", "e"]
    assert x[-6:] == ["a", "b", "c", "d", "e"]
    assert x[-999:] == ["a", "b", "c", "d", "e"]


def test_split_negative_stop():
    x = ["a", "b", "c", "d", "e"]
    assert x[:-1] == ["a", "b", "c", "d"]
    assert x[:-2] == ["a", "b", "c"]
    assert x[:-3] == ["a", "b"]
    assert x[:-4] == ["a"]
    assert x[:-5] == []
    assert x[:-999] == []


def test_split_middle():
    x = ["a", "b", "c", "d", "e"]
    assert x[-999:999] == ["a", "b", "c", "d", "e"]
    assert x[1:3] == ["b", "c"]
    assert x[-4:-2] == ["b", "c"]
    assert x[-4:3] == ["b", "c"]
    assert x[1:-2] == ["b", "c"]
    assert x[1:-3] == ["b"]
    assert x[-4:2] == ["b"]
    assert x[1:-4] == []
    assert x[1:-5] == []
    assert x[2:-3] == []
    assert x[3:-3] == []


def test_concat():
    assert ["a", "b", "c"] + ["x", "y", "z"] == ["a", "b", "c", "x", "y", "z"]


def test_repeat():
    assert ["a", "b", "c"] * 3 == ["a", "b", "c", "a", "b", "c", "a", "b", "c"]


def test_reverse():
    x = ["a", "b", "c", "d", "e"]
    x.reverse()
    assert x == ["e", "d", "c", "b", "a"]


# append() adds a single element to the end of a list.
def test_append():
    x = ["a", "b", "c"]
    x.append("z")
    assert x == ["a", "b", "c", "z"]


# extend() adds multiple elements to the end of a list.
def test_extend():
    x = ["a", "b", "c"]
    x.extend(["x", "y", "z"])
    assert x == ["a", "b", "c", "x", "y", "z"]


def test_clear():
    x = ["a", "b", "c"]
    x.clear()
    assert x == []


def test_copy():
    x = ["a", "b", "c"]
    y = x.copy()
    y.append("z")
    assert x == ["a", "b", "c"]
    assert y == ["a", "b", "c", "z"]


def test_pop():
    x = ["a", "b", "c"]
    assert x.pop() == "c"
    assert x == ["a", "b"]
    assert x.pop() == "b"
    assert x == ["a"]
    assert x.pop() == "a"
    assert x == []
    with pytest.raises(IndexError) as e:
        x.pop()
    assert "pop from empty list" in str(e.value)


def test_remove():
    x = ["a", "b", "a", "c", "a"]
    x.remove("a")
    assert x == ["b", "a", "c", "a"]
    x.remove("a")
    assert x == ["b", "c", "a"]
    x.remove("a")
    assert x == ["b", "c"]
    with pytest.raises(ValueError):
        x.remove("a")


def test_remove_not_in_list():
    with pytest.raises(ValueError) as e:
        ["apple", "banana", "orange"].remove("lemon")
    assert "x not in list" in str(e.value)


# The argument to .remove() indicates the value,  rather than the index, of the element to remove.
# .remove(2) removes the element with the value 2, rather than the element at index 2.
def test_remove_integer():
    x = [1, 4, 0, 2, 3]
    x.remove(2)
    assert x == [1, 4, 0, 3]


def test_insert():
    x = ["a", "b", "c"]
    x.insert(0, "apple")
    assert x == ["apple", "a", "b", "c"]
    x.insert(2, "banana")
    assert x == ["apple", "a", "banana", "b", "c"]
    x.insert(999, "lemon")
    assert x == ["apple", "a", "banana", "b", "c", "lemon"]
    x.insert(-999, "kiwi")
    assert x == ["kiwi", "apple", "a", "banana", "b", "c", "lemon"]


# TODO examples with "start" and "stop" arguments
def test_index():
    assert ["a", "b", "c"].index("a") == 0
    assert ["a", "b", "c"].index("b") == 1
    assert ["a", "b", "c"].index("c") == 2
    assert ["a", "b", "c", "a"].index("a") == 0


def test_index_not_in_list():
    with pytest.raises(ValueError) as e:
        ["apple", "banana", "orange"].index("lemon")
    assert "'lemon' is not in list" in str(e.value)


def test_sort():
    x = ["apple", "pear", "banana", "kiwi", "lemon"]
    x.sort()
    assert x == ["apple", "banana", "kiwi", "lemon", "pear"]


def test_sort_reverse():
    x = ["apple", "pear", "banana", "kiwi", "lemon"]
    x.sort(reverse=True)
    assert x == ["pear", "lemon", "kiwi", "banana", "apple"]


def test_sort_by_key():
    x = ["apple", "pear", "banana", "kiwi", "lemon"]
    x.sort(key=len)
    assert x == ["pear", "kiwi", "apple", "lemon", "banana"]


def test_count():
    assert ["a", "b", "c"].count("a") == 1
    assert ["a", "b", "c"].count("b") == 1
    assert ["a", "b", "c"].count("c") == 1
    assert ["a", "b", "c"].count("z") == 0
    assert ["a", "X", "b", "c", "X", "d", "e", "X", "f"].count("X") == 3
