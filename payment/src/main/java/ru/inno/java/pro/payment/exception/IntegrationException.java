package ru.inno.java.pro.payment.exception;

public class IntegrationException extends RuntimeException {
  private final String localReason;

  public IntegrationException(String localReason, String message) {
    super(message);
    this.localReason = localReason;
  }

  public String getLocalReason() {
    return localReason;
  }
}
