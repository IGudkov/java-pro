package ru.inno.java.pro.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class JavaProConfiguration {
  @Bean
  public DataSource dataSource() {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl("jdbc:postgresql://localhost:5432/postgres");
    dataSource.setSchema("java_pro");
    dataSource.setUsername("admin");
    dataSource.setPassword("1234");
    return dataSource;
  }
}
