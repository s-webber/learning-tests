# See: https://docs.python.org/2/library/string.html
import pytest


def test_capitalize():
    assert "hello world!".capitalize() == "Hello world!"
    assert "HELLO WORLD!".capitalize() == "Hello world!"


def test_title():
    assert "hello world!".title() == "Hello World!"
    assert "HELLO WORLD!".title() == "Hello World!"


def test_swapcase():
    assert "HELLO world!".swapcase() == "hello WORLD!"


def test_center():
    assert "".center(3) == "   "
    assert "a".center(3) == " a "
    assert "ab".center(3) == " ab"
    assert "abc".center(3) == "abc"
    assert "abcd".center(3) == "abcd"


def test_ljust():
    assert "hello".ljust(4) == "hello"
    assert "hello".ljust(5) == "hello"
    assert "hello".ljust(6) == "hello "
    assert "hello".ljust(7) == "hello  "
    assert "hello".ljust(10) == "hello     "
    assert "hello".ljust(10, "*") == "hello*****"


def test_ljust_fill_character_wrong_length():
    with pytest.raises(TypeError) as e:
        "hello".ljust(10, "12")
    assert "The fill character must be exactly one character long" in str(e.value)


def test_rjust():
    assert "hello".rjust(4) == "hello"
    assert "hello".rjust(5) == "hello"
    assert "hello".rjust(6) == " hello"
    assert "hello".rjust(7) == "  hello"
    assert "hello".rjust(10) == "     hello"
    assert "hello".rjust(10, "*") == "*****hello"


def test_rjust_fill_character_wrong_length():
    with pytest.raises(TypeError) as e:
        "hello".rjust(10, "12")
    assert "The fill character must be exactly one character long" in str(e.value)


def test_lower():
    assert "hello".lower() == "hello"
    assert "Hello".lower() == "hello"
    assert "HELLO".lower() == "hello"
    assert "Hello world!".lower() == "hello world!"


def test_upper():
    assert "hello".upper() == "HELLO"
    assert "Hello".upper() == "HELLO"
    assert "HELLO".upper() == "HELLO"
    assert "Hello world!".upper() == "HELLO WORLD!"


def test_startswith():
    assert "bananas".startswith("b")
    assert "bananas".startswith("banana")
    assert "bananas".startswith("bananas")
    assert not "bananas".startswith("x")
    assert not "bananas".startswith("ananas")
    assert "bananas".startswith("ananas", 1)


def test_startswith_tuple():
    assert "bananas".startswith(("bar", "ban", "bin"))
    assert not "bananas".startswith(("bar", "band", "bin"))


def test_endswith():
    assert "bananas".endswith("s")
    assert "bananas".endswith("ananas")
    assert "bananas".endswith("bananas")
    assert not "bananas".endswith("x")
    assert not "bananas".endswith("banana")
    assert "bananas".endswith("nana", 0, 6)


def test_endswith_tuple():
    assert "bananas".endswith(("abc", "nas", "xyz"))
    assert not "bananas".endswith(("abc", "mas", "xyz"))


def test_find():
    assert "bananas".find("") == 0
    assert "bananas".find("b") == 0
    assert "bananas".find("bana") == 0
    assert "bananas".find("bananas") == 0
    assert "bananas".find("a") == 1
    assert "bananas".find("ana") == 1
    assert "bananas".find("anas") == 3
    assert "bananas".find("s") == 6
    assert "bananas".find("x") == -1
    assert "bananas".find("bananas in pyjamas") == -1


def test_rfind():
    assert "bananas".rfind("") == 7
    assert "bananas".rfind("b") == 0
    assert "bananas".rfind("bana") == 0
    assert "bananas".rfind("bananas") == 0
    assert "bananas".find("a") == 1
    assert "bananas".rfind("ana") == 3
    assert "bananas".rfind("anas") == 3
    assert "bananas".rfind("s") == 6
    assert "bananas".rfind("x") == -1
    assert "bananas".rfind("bananas in pyjamas") == -1


# index() is like find() but raises ValueError when the substring is not found.
def test_index():
    assert "bananas".index("") == 0
    assert "bananas".index("b") == 0
    assert "bananas".index("bana") == 0
    assert "bananas".index("bananas") == 0
    assert "bananas".index("a") == 1
    assert "bananas".index("ana") == 1
    assert "bananas".index("anas") == 3
    assert "bananas".index("s") == 6
    with pytest.raises(ValueError):
        "bananas".index("x")
    with pytest.raises(ValueError):
        "bananas".index("bananas in pyjamas")


# rindex() is like rfind() but raises ValueError when the substring is not found.
def test_rindex():
    assert "bananas".rindex("") == 7
    assert "bananas".rindex("b") == 0
    assert "bananas".rindex("bana") == 0
    assert "bananas".rindex("bananas") == 0
    assert "bananas".rindex("a") == 5
    assert "bananas".rindex("ana") == 3
    assert "bananas".rindex("anas") == 3
    assert "bananas".rindex("s") == 6
    with pytest.raises(ValueError):
        "bananas".rindex("x")
    with pytest.raises(ValueError):
        "bananas".rindex("bananas in pyjamas")


def test_count():
    assert "".count("") == 1
    assert "bananas".count("") == 8
    assert "bananas".count("bananas") == 1
    assert "bananas".count("a") == 3
    assert "bananas".count("an") == 2
    assert "bananas".count("ana") == 1
    assert "bananas".count("anas") == 1
    assert "bananas".count("x") == 0
    assert "bananas".count("bananas in pyjamas") == 0


def test_isalpha():
    assert "hello".isalpha()
    assert "Hello".isalpha()
    assert "HELLO".isalpha()
    assert not "hello!".isalpha()
    assert not "Hello world".isalpha()
    assert not "123".isalpha()


def test_partition():
    assert "bananas".partition("na") == ('ba', 'na', 'nas')
    assert "bananas".partition("x") == ('bananas', '', '')


def test_rpartition():
    assert "bananas".rpartition("na") == ('bana', 'na', 's')
    assert "bananas".rpartition("x") == ('', '', 'bananas')


def test_in():
    assert "bananas" in "bananas"
    assert "na" in "bananas"
    assert "" in "bananas"
    assert "x" not in "bananas"
    assert "bananas in pyjamas" not in "bananas"


def test_len():
    assert len("") == 0
    assert len("a") == 1
    assert len("bananas") == 7


def test_join():
    assert "a".join(("b", "n", "n", "s")) == "bananas"
    assert "n".join(("ba", "a", "as")) == "bananas"
    assert "nan".join(("ba", "as")) == "bananas"
    assert "x".join("b") == "b"
    assert "x".join("bananas") == "bxaxnxaxnxaxs"


def test_split():
    assert "bananas".split("a") == ["b", "n", "n", "s"]
    assert "bananas".split("ana") == ["b", "nas"]
    assert "bananas".split("a", 2) == ["b", "n", "nas"]


# If no separator is specified to split() then runs of consecutive whitespace are regarded as a single separator,
# and the result will contain no empty strings at the start or end if the string has leading or trailing whitespace.
def test_split_without_separator():
    assert " The quick brown\nfox   jumps over the lazy dog ".split() == \
           ["The", "quick", "brown", "fox", "jumps", "over", "the", "lazy", "dog"]
    assert "       ".split() == []
    assert "       ".split(None) == []


def test_rsplit():
    assert "bananas".rsplit("a") == ["b", "n", "n", "s"]
    assert "bananas".rsplit("ana") == ["ban", "s"]
    assert "bananas".rsplit("a", 2) == ["ban", "n", "s"]


# for thorough explanation see:
# https://docs.python.org/3.4/library/string.html#formatspec
# https://pyformat.info/
def test_format():
    assert "{0} {1}!".format("hello", "world") == "hello world!"
    assert "{1} {0}!".format("hello", "world") == "world hello!"


def test_format_map():
    assert "{item1} {item2}".format_map({"item1": "hello", "item2": "world"})
