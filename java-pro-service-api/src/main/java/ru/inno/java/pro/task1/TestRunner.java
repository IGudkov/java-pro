package ru.inno.java.pro.task1;

import ru.inno.java.pro.task1.annotations.AfterSuite;
import ru.inno.java.pro.task1.annotations.AfterTest;
import ru.inno.java.pro.task1.annotations.BeforeSuite;
import ru.inno.java.pro.task1.annotations.BeforeTest;
import ru.inno.java.pro.task1.annotations.CsvSource;
import ru.inno.java.pro.task1.annotations.Test;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunner {
  public static final int TEST_PRIORITY_MIN = 1;
  public static final int TEST_PRIORITY_MAX = 10;

  public static void runTests(Class<?> c) throws ReflectiveOperationException {
    Object instance = c.getDeclaredConstructor().newInstance();
    Method[] methods = c.getDeclaredMethods();

    Method beforeSuiteMethod = null;
    Method afterSuiteMethod = null;

    List<Method> testMethods = new ArrayList<>();
    List<Method> beforeTestMethods = new ArrayList<>();
    List<Method> afterTestMethods = new ArrayList<>();

    for (Method method : methods) {
      if (method.isAnnotationPresent(BeforeSuite.class)) {
        beforeSuiteMethod = getSingleStaticMethod(beforeSuiteMethod, method, BeforeSuite.class);
      }
      if (method.isAnnotationPresent(AfterSuite.class)) {
        afterSuiteMethod = getSingleStaticMethod(afterSuiteMethod, method, AfterSuite.class);
      }
      if (method.isAnnotationPresent(BeforeTest.class)) {
        beforeTestMethods.add(method);
      }
      if (method.isAnnotationPresent(AfterTest.class)) {
        afterTestMethods.add(method);
      }
      if (method.isAnnotationPresent(Test.class)) {
        testMethods.add(getTestMethod(method));
      }
    }

    invokeStaticMethodIfNotNull(beforeSuiteMethod);

    testMethods.sort(Comparator.comparingInt(m -> m.getAnnotation(Test.class).priority()));

    for (Method testMethod : testMethods) {
      invokeInstanceMethods(beforeTestMethods, instance);
      invokeTestMethod(testMethod, instance);
      invokeInstanceMethods(afterTestMethods, instance);
    }

    invokeStaticMethodIfNotNull(afterSuiteMethod);
  }

  private static Object convertToType(String value, Class<?> type) {
    if (type.equals(Integer.class) || type.equals(int.class)) {
      return Integer.parseInt(value);
    } else if (type.equals(Double.class) || type.equals(double.class)) {
      return Double.parseDouble(value);
    } else if (type.equals(Boolean.class) || type.equals(boolean.class)) {
      return Boolean.parseBoolean(value);
    } else if (type.equals(String.class)) {
      return value;
    } else {
      throw new IllegalArgumentException("Недопустимый тип: " + type.getName());
    }
  }

  private static Method getSingleStaticMethod(Method singleStaticMethod, Method checkMethod, Class<?> annotationClass) {
    if (singleStaticMethod == null) {
      checkMethodIsStatic(checkMethod, annotationClass);
      return checkMethod;
    } else {
      String errorMessage = String.format("Методов с аннотацией %s больше одного", annotationClass.getSimpleName());
      throw new IllegalArgumentException(errorMessage);
    }
  }

  private static void checkMethodIsStatic(Method method, Class<?> annotationClass) {
    if (!isStaticMethod(method)) {
      throw new IllegalArgumentException(String.format("Метод %s: аннотация %s применима только к методам static",
          method.getName(),
          annotationClass.getSimpleName()));
    }
  }

  private static Method getTestMethod(Method checkMethod) {
    if (isStaticMethod(checkMethod)) {
      String errorMessage = String.format("Метод %s: аннотация %s не применима к методам static",
          checkMethod.getName(),
          Test.class.getSimpleName());
      throw new IllegalArgumentException(errorMessage);
    }
    int priority = checkMethod.getAnnotation(Test.class).priority();
    if (priority < TEST_PRIORITY_MIN || priority > TEST_PRIORITY_MAX) {
      String errorMessage = String.format("Метод %s: в аннотации %s указано недопустимое значение priority=%s (допустимые значения от %s до %s)"
          , checkMethod.getName()
          , Test.class.getSimpleName()
          , priority
          , TEST_PRIORITY_MIN
          , TEST_PRIORITY_MAX);
      throw new IllegalArgumentException(errorMessage);
    }
    return checkMethod;
  }

  private static boolean isStaticMethod(Method method) {
    return Modifier.isStatic(method.getModifiers());
  }

  private static void invokeStaticMethodIfNotNull(Method method) throws ReflectiveOperationException {
    if (method != null) {
      method.invoke(null);
    }
  }

  private static void invokeTestMethod(Method method, Object instance) throws ReflectiveOperationException {
    if (method.isAnnotationPresent(CsvSource.class)) {
      CsvSource csvSource = method.getAnnotation(CsvSource.class);
      String[] values = csvSource.value().split(",\\s*");
      Class<?>[] parameterTypes = method.getParameterTypes();
      Object[] parameters = new Object[values.length];

      for (int i = 0; i < values.length; i++) {
        try {
          parameters[i] = convertToType(values[i], parameterTypes[i]);
        } catch (IllegalArgumentException e) {
          String errorMessage = String.format("Метод %s, аннотация %s: %s",
              method.getName(),
              CsvSource.class.getSimpleName(),
              e.getMessage());
          throw new IllegalArgumentException(errorMessage);
        }
      }

      method.invoke(instance, parameters);
    } else {
      method.invoke(instance);
    }
  }

  private static void invokeInstanceMethods(List<Method> methods, Object instance) throws ReflectiveOperationException {
    for (Method method : methods) {
      method.invoke(instance);
    }
  }
}
