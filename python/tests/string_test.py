def test_capitalize():
    assert "hello world!".capitalize() == "Hello world!"
    assert "HELLO WORLD!".capitalize() == "Hello world!"


def test_title():
    assert "hello world!".title() == "Hello World!"
    assert "HELLO WORLD!".title() == "Hello World!"


def test_isalpha():
    assert "hello".isalpha()
    assert "Hello".isalpha()
    assert "HELLO".isalpha()
    assert not "hello!".isalpha()
    assert not "Hello world".isalpha()
    assert not "123".isalpha()


def test_lower():
    assert "hello".lower() == "hello"
    assert "Hello".lower() == "hello"
    assert "HELLO".lower() == "hello"
    assert "Hello world!".lower() == "hello world!"


def test_lower():
    assert "hello".upper() == "HELLO"
    assert "Hello".upper() == "HELLO"
    assert "HELLO".upper() == "HELLO"
    assert "Hello world!".upper() == "HELLO WORLD!"


def test_find():
    assert "bananas".find("") == 0
    assert "bananas".find("b") == 0
    assert "bananas".find("bananas") == 0
    assert "bananas".find("ana") == 1
    assert "bananas".find("anas") == 3
    assert "bananas".find("s") == 6
    assert "bananas".find("x") == -1
    assert "bananas".find("bananas in pyjamas") == -1


def test_center():
    assert "".center(3) == "   "
    assert "a".center(3) == " a "
    assert "ab".center(3) == " ab"
    assert "abc".center(3) == "abc"
    assert "abcd".center(3) == "abcd"


def test_count():
    assert "".count("") == 1
    assert "bananas".count("") == 8
    assert "bananas".count("bananas") == 1
    assert "bananas".count("a") == 3
    assert "bananas".count("an") == 2
    assert "bananas".count("ana") == 1
    assert "bananas".count("anas") == 1
    assert "bananas".count("x") == 0
