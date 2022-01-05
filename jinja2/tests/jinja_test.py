from jinja2 import Environment, FileSystemLoader, select_autoescape


def test_autoescape():
    env = create_environment()
    template = env.get_template("autoescape_example.html")
    output = template.render({"x": "<b>world</b>"})
    assert output == "hello &lt;b&gt;world&lt;/b&gt;\nhello <b>world</b>"


def test_if():
    env = create_environment()
    template = env.get_template("if_example.html")
    assert template.render({"i": -1}) == "\n  negative\n"
    assert template.render({"i":  0}) == "\n  zero\n"
    assert template.render({"i":  1}) == "\n  positive\n"


def test_for():
    env = create_environment()
    template = env.get_template("for_example.html")
    output = template.render({"numbers": [2, 6, 7]})
    assert output == " 4  36  49 "


def test_extends():
    env = create_environment()
    template = env.get_template("extends_example.html")
    output = template.render({"name": "world"})
    assert output == \
"""<!DOCTYPE html>
<html>
<head>
<title> Example of using extends </title>
</head>
<body>


  Hello, world!


</body>
</html>"""


def create_environment():
    return Environment(
        loader=FileSystemLoader(searchpath="templates"),
        autoescape=select_autoescape(["html"]),
    )
