package ru.inno.java.pro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.inno.java.pro.model.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
  List<Product> findByUserId(Long userId);
}
