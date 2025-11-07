package ru.inno.java.pro.task2;

import ru.inno.java.pro.task2.enums.Post;

public class Employee {
  private String name;
  private int age;
  private Post post;

  public Employee(String name, int age, Post post) {
    this.name = name;
    this.age = age;
    this.post = post;
  }

  public String getName() {
    return name;
  }

  public int getAge() {
    return age;
  }

  public Post getPost() {
    return post;
  }

  @Override
  public String toString() {
    return String.format("имя=%s; возраст=%s; должность=%s", name, age, post);
  }
}
