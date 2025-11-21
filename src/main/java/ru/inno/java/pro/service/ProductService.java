package ru.inno.java.pro.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import ru.inno.java.pro.mapper.ProductMapper;
import ru.inno.java.pro.model.dto.ProductDto;
import ru.inno.java.pro.model.entity.Product;
import ru.inno.java.pro.repository.ProductRepository;

import java.util.List;

@Service
public class ProductService {
  private final ProductRepository repository;
  private final ProductMapper mapper;

  public ProductService(ProductRepository repository, ProductMapper mapper) {
    this.repository = repository;
    this.mapper = mapper;
  }

  public List<ProductDto> getProductsByUserId(Long userId) {
    return repository.findByUserId(userId)
        .stream()
        .map(mapper::fromEntity)
        .toList();
  }

  public ProductDto getProductById(Long productId) {
    Product product = repository.findById(productId)
        .orElseThrow(() -> new EntityNotFoundException(String.format("Продукт id=%d не найден", productId)));
    return mapper.fromEntity(product);
  }
}
