package ru.inno.java.pro.service;

import org.springframework.stereotype.Service;
import ru.inno.java.pro.dao.UserDao;
import ru.inno.java.pro.model.User;

import java.sql.SQLException;
import java.util.List;

@Service
public class UserService {
  private final UserDao userDao;

  public UserService(UserDao userDao) {
    this.userDao = userDao;
  }

  public void createUser(User user) throws SQLException {
    userDao.save(user);
  }

  public void deleteUser(Long id) throws SQLException {
    userDao.deleteById(id);
  }

  public User getUserById(Long id) throws SQLException {
    return userDao.findById(id);
  }

  public User getUserByUserName(String userName) throws SQLException {
    return userDao.findByUserName(userName);
  }

  public List<User> getAllUsers() throws SQLException {
    return userDao.findAll();
  }
}
