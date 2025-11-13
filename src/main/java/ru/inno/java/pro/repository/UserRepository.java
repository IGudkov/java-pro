package ru.inno.java.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inno.java.pro.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);
}
