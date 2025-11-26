package ru.inno.java.pro.service;

import org.springframework.stereotype.Service;
import ru.inno.java.pro.model.entity.User;
import ru.inno.java.pro.repository.UserRepository;

import java.util.List;

@Service
public class UserService {
  private final UserRepository repository;

  public UserService(UserRepository repository) {
    this.repository = repository;
  }

  public void createUser(User user) {
    String username = user.getUsername();

    if (username == null || username.isEmpty()) {
      throw new IllegalArgumentException("username не может быть null или пустым");
    }

    User existingUser = repository.findByUsername(username).orElse(null);

    if (existingUser != null) {
      String details = String.format("Пользователь с username=%s уже существует", username);
      throw new IllegalArgumentException(details);
    }

    repository.save(user);
  }

  public void deleteUser(Long id) {
    repository.deleteById(id);
  }

  public User getUserById(Long id) {
    return repository.findById(id).orElse(null);
  }

  public User getUserByUserName(String username) {
    return repository.findByUsername(username).orElse(null);
  }

  public List<User> getAllUsers() {
    return repository.findAll();
  }
}
