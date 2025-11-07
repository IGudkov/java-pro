package ru.inno.java.pro.configuration;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource(value = "classpath:application.properties")
public class JavaProConfiguration {

  @Value("${app.datasource.url}")
  private String jdbcUrl;

  @Value("${app.datasource.schema}")
  private String schema;

  @Value("${app.datasource.username}")
  private String username;

  @Value("${app.datasource.password}")
  private String password;

  @Bean
  public DataSource dataSource() {
    HikariDataSource dataSource = new HikariDataSource();
    dataSource.setJdbcUrl(jdbcUrl);
    dataSource.setSchema(schema);
    dataSource.setUsername(username);
    dataSource.setPassword(password);
    return dataSource;
  }
}
