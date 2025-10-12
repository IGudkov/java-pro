package ru.inno.java.pro.task1;

import ru.inno.java.pro.task1.annotations.AfterSuite;
import ru.inno.java.pro.task1.annotations.AfterTest;
import ru.inno.java.pro.task1.annotations.BeforeSuite;
import ru.inno.java.pro.task1.annotations.BeforeTest;
import ru.inno.java.pro.task1.annotations.CsvSource;
import ru.inno.java.pro.task1.annotations.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class TestRunner {
  private static final Class<BeforeSuite> BEFORE_SUITE_CLASS = BeforeSuite.class;
  private static final Class<AfterSuite> AFTER_SUITE_CLASS = AfterSuite.class;
  private static final Class<BeforeTest> BEFORE_TEST_CLASS = BeforeTest.class;
  private static final Class<AfterTest> AFTER_TEST_CLASS = AfterTest.class;
  private static final Class<Test> TEST_CLASS = Test.class;
  private static final Class<CsvSource> CSV_SOURCE_CLASS = CsvSource.class;
  public static final int TEST_PRIORITY_MIN = 1;
  public static final int TEST_PRIORITY_MAX = 10;

  public static void runTests(Class<?> c) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    Object instance = c.getDeclaredConstructor().newInstance();
    Method[] methods = c.getDeclaredMethods();

    Method beforeSuiteMethod = null;
    Method afterSuiteMethod = null;

    List<Method> testMethods = new ArrayList<>();
    List<Method> beforeTestMethods = new ArrayList<>();
    List<Method> afterTestMethods = new ArrayList<>();

    for (Method method : methods) {
      if (method.isAnnotationPresent(BEFORE_SUITE_CLASS)) {
        beforeSuiteMethod = getSingleStaticMethod(beforeSuiteMethod, method, BEFORE_SUITE_CLASS);
      }
      if (method.isAnnotationPresent(AFTER_SUITE_CLASS)) {
        afterSuiteMethod = getSingleStaticMethod(afterSuiteMethod, method, AFTER_SUITE_CLASS);
      }
      if (method.isAnnotationPresent(BEFORE_TEST_CLASS)) {
        beforeTestMethods.add(method);
      }
      if (method.isAnnotationPresent(AFTER_TEST_CLASS)) {
        afterTestMethods.add(method);
      }
      if (method.isAnnotationPresent(TEST_CLASS)) {
        testMethods.add(getTestMethod(method));
      }
    }

    invokeStaticMethodIfNotNull(beforeSuiteMethod);

    testMethods.sort(Comparator.comparingInt(m -> m.getAnnotation(TEST_CLASS).priority()));

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
          TEST_CLASS.getSimpleName());
      throw new IllegalArgumentException(errorMessage);
    }
    int priority = checkMethod.getAnnotation(TEST_CLASS).priority();
    if (priority < TEST_PRIORITY_MIN || priority > TEST_PRIORITY_MAX) {
      String errorMessage = String.format("Метод %s: в аннотации %s указано недопустимое значение priority=%s (допустимые значения от %s до %s)"
          , checkMethod.getName()
          , TEST_CLASS.getSimpleName()
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

  private static void invokeStaticMethodIfNotNull(Method method) throws InvocationTargetException, IllegalAccessException {
    if (method != null) {
      method.invoke(null);
    }
  }

  private static void invokeTestMethod(Method method, Object instance) throws InvocationTargetException, IllegalAccessException {
    if (method.isAnnotationPresent(CSV_SOURCE_CLASS)) {
      CsvSource csvSource = method.getAnnotation(CSV_SOURCE_CLASS);
      String[] values = csvSource.value().split(",\\s*");
      Class<?>[] parameterTypes = method.getParameterTypes();
      Object[] parameters = new Object[values.length];

      for (int i = 0; i < values.length; i++) {
        try {
          parameters[i] = convertToType(values[i], parameterTypes[i]);
        } catch (IllegalArgumentException e) {
          String errorMessage = String.format("Метод %s, аннотация %s: %s",
              method.getName(),
              CSV_SOURCE_CLASS.getSimpleName(),
              e.getMessage());
          throw new IllegalArgumentException(errorMessage);
        }
      }

      method.invoke(instance, parameters);
    } else {
      method.invoke(instance);
    }
  }

  private static void invokeInstanceMethods(List<Method> methods, Object instance) throws InvocationTargetException, IllegalAccessException {
    for (Method method : methods) {
      method.invoke(instance);
    }
  }
}
