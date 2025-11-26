package ru.inno.java.pro.exception;

public class InsufficientFundsProductException extends RuntimeException {
  public InsufficientFundsProductException(String message) {
    super(message);
  }
}
