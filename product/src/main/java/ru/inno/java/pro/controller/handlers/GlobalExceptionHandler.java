package ru.inno.java.pro.controller.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.inno.java.pro.exception.InsufficientFundsProductException;
import ru.inno.java.pro.exception.ProductNotFoundException;
import ru.inno.java.pro.model.dto.IntegrationErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(ProductNotFoundException.class)
  public IntegrationErrorResponseDto handleProductNotFoundException(ProductNotFoundException e) {
    return new IntegrationErrorResponseDto("Продукт не найден", e.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(InsufficientFundsProductException.class)
  public IntegrationErrorResponseDto handleInsufficientFundsProductException(InsufficientFundsProductException e) {
    return new IntegrationErrorResponseDto("Недостаточно средств", e.getMessage());
  }
}
