package ru.inno.java.pro.task1;

import java.lang.reflect.InvocationTargetException;

public class App {
  public static void main(String[] args) throws InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
    TestRunner.runTests(TestClass.class);
  }
}
