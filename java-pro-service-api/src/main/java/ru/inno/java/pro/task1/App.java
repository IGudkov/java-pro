package ru.inno.java.pro.task1;

import java.lang.reflect.InvocationTargetException;

public class App {
  public static void main(String[] args) throws ReflectiveOperationException {
    TestRunner.runTests(TestClass.class);
  }
}
