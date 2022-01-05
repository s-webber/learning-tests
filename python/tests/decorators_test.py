import functools


def function_decorator(f):
   """
   Example of using a function as a decorator.
   """

   def wrap(*args, **kwargs):
      x = f(*args, **kwargs)
      return '*' + x + '*'
   return wrap


@function_decorator
def decorated_with_function_decorator():
    return 'test'


def test_function_decorator():
    assert '*test*' == decorated_with_function_decorator()


class ClassDecorator:
    """
    Example of using a class as a decorator.
    """

    def __init__(self, f):
       self.f = f
       self.count = 0

    def __call__(self, *args, **kwargs):
        self.count += 1


@ClassDecorator
def decorated_with_class_decorator():
    return 'test'


def test_class_decorator():
    assert decorated_with_class_decorator.count == 0
    decorated_with_class_decorator()
    assert decorated_with_class_decorator.count == 1
    decorated_with_class_decorator()
    assert decorated_with_class_decorator.count == 2
    decorated_with_class_decorator()
    assert decorated_with_class_decorator.count == 3


class InstanceDecorator:
    """
    Example of using a class instance as a decorator.
    """

    def __init__(self, token):
       self.token = token

    def __call__(self, f):
        def wrap(*args, **kwargs):
            x = f(*args, **kwargs)
            return self.token + x + self.token
        return wrap

instance_decorator = InstanceDecorator('*')

@instance_decorator
def decorated_with_instance_decorator():
    return 'test'

def test_instance_decorator():
    assert decorated_with_instance_decorator() == '*test*'
    instance_decorator.token = '-'
    assert decorated_with_instance_decorator() == '-test-'


def factory_function_for_decorators(token):
   def decorator(f):
      def wrap(*args, **kwargs):
         x = f(*args, **kwargs)
         return token + x + token
      return wrap
   return decorator

@factory_function_for_decorators('^third^')
@factory_function_for_decorators('-second-')
@factory_function_for_decorators('*first*')
def decorated_with_mulitiple_decorators():
    return 'test'

def test_multiple_decorators():
    """
    Confirm that multiple decorators can be applied to a function
    and that they are processed in the reverse order that they are specified.
    """
    assert decorated_with_mulitiple_decorators() == '^third^-second-*first*test*first*-second-^third^'


def decorator_without_functools_wrap(f):
   def x(*args, **kwargs):
      """Docstring for x"""
      return f()
   return x
   

@decorator_without_functools_wrap
def function_without_functools_wrap():
    """Docstring for decorator_without_functools_wrap"""
    pass

def decorator_with_functools_wrap(f):
   @functools.wraps(f)
   def y(*args, **kwargs):
      """Docstring for y"""
      return f()
   return y

@decorator_with_functools_wrap
def function_with_functools_wrap():
    """Docstring for function_with_functools_wrap"""
    pass

def test_metadata():
    """Confirm that functools.wraps maintains the metadata of the wrapped function"""
    assert function_without_functools_wrap.__name__ == 'x'
    assert function_without_functools_wrap.__doc__ == 'Docstring for x'
    assert function_with_functools_wrap.__name__ == 'function_with_functools_wrap'
    assert function_with_functools_wrap.__doc__ == 'Docstring for function_with_functools_wrap'
