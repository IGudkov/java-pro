package ru.inno.java.pro.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.*;
import ru.inno.java.pro.model.dto.PaymentRequestDto;
import ru.inno.java.pro.model.dto.PaymentResponseDto;
import ru.inno.java.pro.model.dto.ProductDto;
import ru.inno.java.pro.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
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
  public ProductDto getProductById(@PathVariable Long productId) {
    return service.getProductById(productId);
  }

  @PostMapping(value = "/execute-payment")
  public PaymentResponseDto executePayment(@RequestBody PaymentRequestDto paymentRequest) throws JsonProcessingException {
    return service.executePayment(paymentRequest);
  }
}
