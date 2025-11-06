package ru.inno.java.pro.dao;

import org.springframework.stereotype.Component;
import ru.inno.java.pro.model.User;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDao {
  private final DataSource dataSource;
  private static final String COLUMN_LABEL_ID = "id";
  private static final String COLUMN_LABEL_USERNAME = "username";

  public UserDao(DataSource dataSource) {
    this.dataSource = dataSource;
  }

  public void save(User user) throws SQLException {
    String userName = user.getUsername();

    if (userName == null || userName.isEmpty()) {
      throw new IllegalArgumentException("username не может быть null или пустым");
    }

    User existingUser = findByUserName(userName);

    if (existingUser != null) {
      String details = String.format("Пользователь с username=%s уже существует", userName);
      throw new IllegalArgumentException(details);
    }

    String sql = "INSERT INTO users (username) VALUES (?)";
    Connection connection = dataSource.getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, user.getUsername());
    preparedStatement.executeUpdate();
  }

  public User findByUserName(String userName) throws SQLException {
    String sql = "SELECT * FROM users WHERE username = ?";
    Connection connection = dataSource.getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setString(1, userName);
    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
      User user = new User();
      user.setId(resultSet.getLong(COLUMN_LABEL_ID));
      user.setUsername(resultSet.getString(COLUMN_LABEL_USERNAME));
      return user;
    }
    return null;
  }

  public User findById(Long id) throws SQLException {
    String sql = "SELECT * FROM users WHERE id = ?";
    Connection connection = dataSource.getConnection();
    PreparedStatement preparedStatement = connection.prepareStatement(sql);
    preparedStatement.setLong(1, id);
    ResultSet resultSet = preparedStatement.executeQuery();
    if (resultSet.next()) {
      User user = new User();
      user.setId(resultSet.getLong(COLUMN_LABEL_ID));
      user.setUsername(resultSet.getString(COLUMN_LABEL_USERNAME));
      return user;
    }
    return null;
  }

  public List<User> findAll() throws SQLException {
    String sql = "SELECT * FROM users";
    List<User> users = new ArrayList<>();
    Connection connection = dataSource.getConnection();
    PreparedStatement ps = connection.prepareStatement(sql);
    ResultSet rs = ps.executeQuery();
    while (rs.next()) {
      User user = new User();
      user.setId(rs.getLong(COLUMN_LABEL_ID));
      user.setUsername(rs.getString(COLUMN_LABEL_USERNAME));
      users.add(user);
    }
    return users;
  }

  public void deleteById(Long id) throws SQLException {
    String sql = "DELETE FROM users WHERE id = ?";
    Connection connection = dataSource.getConnection();
    PreparedStatement ps = connection.prepareStatement(sql);
    ps.setLong(1, id);
    ps.executeUpdate();
  }
}
