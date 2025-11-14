package ru.inno.java.pro;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import ru.inno.java.pro.entity.User;
import ru.inno.java.pro.service.UserService;

import java.util.List;

@Component
public class AppCommandLineRunner implements CommandLineRunner {

  private final UserService userService;

  public AppCommandLineRunner(UserService userService) {
    this.userService = userService;
  }

  @Override
  public void run(String... args) {

    String migrUserName = "MigrUser1";
    User migrUser = userService.getUserByUserName(migrUserName);
    if (migrUser == null) {
      System.out.printf("Пользователь с username=%s не найден%n", migrUserName);
    } else {
      System.out.printf("Найден пользователь с username=%s: %s%n",
          migrUser.getUsername(),
          migrUser);
    }

    User user = new User();
    user.setUsername("UserNameTest3");
    userService.createUser(user);
    System.out.printf("Создан пользователь username=%s%n", user.getUsername());

    User userByUserName = userService.getUserByUserName(user.getUsername());
    if (userByUserName != null) {
      System.out.printf("Найден пользователь с username=%s: %s%n",
          user.getUsername(),
          userByUserName);
    } else {
      System.out.printf("Пользователь с username=%s не найден%n", user.getUsername());
    }

    Long userId = 1L;
    User userById = userService.getUserById(userId);
    if (userById != null) {
      System.out.printf("Найден пользователь с id=%d: %s%n",
          userId,
          userById);
    } else {
      System.out.printf("Пользователь с id=%s не найден%n", userId);
    }

    List<User> users = userService.getAllUsers();
    if (!users.isEmpty()) {
      System.out.println("Найдены пользователи:");
      users.forEach(System.out::println);
    }

    User userForDelete = new User();
    userForDelete.setUsername("UserNameForDelete");
    userService.createUser(userForDelete);
    System.out.printf("Создан пользователь username=%s%n", userForDelete.getUsername());

    User userForDeleteByUserName = userService.getUserByUserName(userForDelete.getUsername());
    if (userForDeleteByUserName != null) {
      System.out.printf("Найден пользователь с username=%s: %s%n",
          userForDelete.getUsername(),
          userForDeleteByUserName);
      userService.deleteUser(userForDeleteByUserName.getId());
      System.out.printf("Пользователь с id=%d удален%n",
          userForDeleteByUserName.getId());
    } else {
      System.out.printf("Пользователь с username=%s не найден%n", userForDelete.getUsername());
    }
  }
}
