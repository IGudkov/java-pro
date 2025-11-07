package ru.inno.java.pro.task1;

import ru.inno.java.pro.task1.annotations.AfterSuite;
import ru.inno.java.pro.task1.annotations.AfterTest;
import ru.inno.java.pro.task1.annotations.BeforeSuite;
import ru.inno.java.pro.task1.annotations.BeforeTest;
import ru.inno.java.pro.task1.annotations.CsvSource;
import ru.inno.java.pro.task1.annotations.Test;

public class TestClass {
  @BeforeSuite
  public static void beforeSuite() {
    System.out.println("beforeSuite method executed");
  }

  @AfterSuite
  public static void afterSuite() {
    System.out.println("afterSuite method executed");
  }

  @BeforeTest
  public void beforeTest() {
    System.out.println("beforeTest method executed");
  }

  @AfterTest
  public void afterTest() {
    System.out.println("afterTest method executed");
  }

  @Test(priority = 1)
  @CsvSource("10, Java, 20, true")
  public void testAndCsvSourcePriority1(int a, String b, int c, boolean d) {
    System.out.println("testAndCsvSourcePriority1 method executed: " + a + ", " + b + ", " + c + ", " + d);
  }

  @Test(priority = 7)
  public void testPriority7() {
    System.out.println("testPriority7 method executed");
  }

  @Test(priority = 3)
  public void testPriority3() {
    System.out.println("testPriority3 method executed");
  }

  @Test()
  public void testPriorityDefault() {
    System.out.println("testPriorityDefault method executed");
  }

  @Test(priority = 9)
  @CsvSource("123, JavaPro, 321, false")
  public void testAndCsvSourcePriority9(int a, String b, int c, boolean d) {
    System.out.println("testAndCsvSourcePriority9 method executed: " + a + ", " + b + ", " + c + ", " + d);
  }
}
