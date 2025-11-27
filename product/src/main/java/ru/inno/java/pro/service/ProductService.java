package ru.inno.java.pro.service;

import org.springframework.stereotype.Service;
import ru.inno.java.pro.exception.InsufficientFundsProductException;
import ru.inno.java.pro.exception.ProductNotFoundException;
import ru.inno.java.pro.mapper.ProductMapper;
import ru.inno.java.pro.model.dto.PaymentRequestDto;
import ru.inno.java.pro.model.dto.PaymentResponseDto;
import ru.inno.java.pro.model.dto.ProductDto;
import ru.inno.java.pro.model.entity.Product;
import ru.inno.java.pro.repository.ProductRepository;

import java.math.BigDecimal;
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
    Product product = getAndCheckProductById(productId);
    return mapper.fromEntity(product);
  }

  public PaymentResponseDto executePayment(PaymentRequestDto paymentRequest) {
    Long productId = paymentRequest.productId();
    BigDecimal amount = paymentRequest.amount();

    Product product = getAndCheckProductById(productId);

    checkProductBalanceByPaymentAmount(product, amount);

    product.setBalance(product.getBalance().subtract(paymentRequest.amount()));
    repository.save(product);
    String message = String.format("Платеж на сумму %.2f исполнен. Доступный остаток %.2f",
        paymentRequest.amount(),
        product.getBalance());
    return new PaymentResponseDto(message);
  }

  private Product getAndCheckProductById(Long productId) {
    return repository.findById(productId)
        .orElseThrow(() -> new ProductNotFoundException(String.format("Продукт ID %d не найден",
            productId)));
  }

  private void checkProductBalanceByPaymentAmount(Product product, BigDecimal amount) {
    if (product.getBalance().compareTo(amount) < 0) {
      throw new InsufficientFundsProductException(String.format("Недостаточно средств на продукте с ID %d",
          product.getId()));
    }
  }
}
