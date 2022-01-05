# Jinja2 Learning Tests

[Jinja](https://pypi.org/project/Jinja2/) is a fast, expressive, extensible templating engine for Python.

To setup a virtual enviroment and install pytest and jinja2:

```
python -m pip install virtualenv
virtualenv.exe venv
venv\Scripts\activate.bat
python -m pip install pytest
python -m pip install jinja2
```

To run the tests:

```
pytest tests
```

To create `requirements.txt`:

```
python -m pip freeze > requirements.txt
```

To use `requirements.txt` as alternative to doing `python -m pip install pytest` and `python -m pip install jinja2`:

```
python -m pip install -r requirements.txt
```
