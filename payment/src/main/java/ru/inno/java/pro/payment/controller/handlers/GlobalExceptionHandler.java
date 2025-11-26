package ru.inno.java.pro.payment.controller.handlers;

import org.apache.hc.client5.http.HttpHostConnectException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import ru.inno.java.pro.payment.dto.integration.IntegrationErrorResponseDto;
import ru.inno.java.pro.payment.exception.IntegrationException;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
  @ExceptionHandler(HttpHostConnectException.class)
  public IntegrationErrorResponseDto handleInternalServerException(HttpHostConnectException e) {
    return new IntegrationErrorResponseDto("Сервер не доступен", e.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(HttpClientErrorException.class)
  public IntegrationErrorResponseDto handleHttpClientErrorException(HttpClientErrorException e) {
    return new IntegrationErrorResponseDto("Ошибка в запросе на стороне пользователя", e.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_REQUEST)
  @ExceptionHandler(IntegrationException.class)
  public IntegrationErrorResponseDto handleIntegrationException(IntegrationException e) {
    return new IntegrationErrorResponseDto(e.getLocalReason(), e.getMessage());
  }

  @ResponseStatus(HttpStatus.BAD_GATEWAY)
  @ExceptionHandler(HttpServerErrorException.class)
  public IntegrationErrorResponseDto handleHttpServerErrorException(HttpServerErrorException e) {
    return new IntegrationErrorResponseDto("Ошибка выполнения запроса на стороне сервера", e.getMessage());
  }
}
