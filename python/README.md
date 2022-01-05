# Python Learning Tests

This projects contains `pytest` unit tests which demonstrate features of the Python programming language.

To setup a virtual enviroment and install pytest:

```
python -m pip install virtualenv
virtualenv.exe venv
venv\Scripts\activate.bat
python -m pip install pytest
```

To run the tests:

```
pytest tests
```

To create `requirements.txt`:

```
python -m pip freeze > requirements.txt
```

To use `requirements.txt` as alternative to doing `python -m pip install pytest`:

```
python -m pip install -r requirements.txt
```
