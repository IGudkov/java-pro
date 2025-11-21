package ru.inno.java.pro.controller.handlers;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.inno.java.pro.model.dto.ControllerErrorResponseDto;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ResponseStatus(HttpStatus.NOT_FOUND)
  @ExceptionHandler(EntityNotFoundException.class)
  public ControllerErrorResponseDto handleEntityNotFoundException(EntityNotFoundException e) {
    return new ControllerErrorResponseDto(e.getMessage());
  }
}
