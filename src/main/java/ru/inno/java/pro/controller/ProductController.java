package ru.inno.java.pro.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.inno.java.pro.model.dto.ProductDto;
import ru.inno.java.pro.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
  private final ProductService service;

  public ProductController(ProductService service) {
    this.service = service;
  }

  @GetMapping(value = "/user-id/{userId}")
  public List<ProductDto> getProductsByUserId(@PathVariable Long userId) {
    return service.getProductsByUserId(userId);
  }

  @GetMapping("/product-id/{productId}")
  public ProductDto getProductsById(@PathVariable Long productId) {
    return service.getProductById(productId);
  }
}
