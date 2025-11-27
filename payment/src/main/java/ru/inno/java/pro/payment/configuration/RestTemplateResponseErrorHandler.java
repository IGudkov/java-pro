package ru.inno.java.pro.payment.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;
import ru.inno.java.pro.payment.dto.integration.IntegrationErrorResponseDto;
import ru.inno.java.pro.payment.exception.IntegrationException;

import java.io.IOException;
import java.net.URI;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {
  private final ObjectMapper objectMapper;

  public RestTemplateResponseErrorHandler(ObjectMapper objectMapper) {
    this.objectMapper = objectMapper;
  }

  @Override
  public boolean hasError(ClientHttpResponse response) throws IOException {
    HttpStatusCode httpStatusCode = response.getStatusCode();
    return httpStatusCode.is4xxClientError() || httpStatusCode.is5xxServerError();
  }

  @Override
  public void handleError(URI url, HttpMethod method, ClientHttpResponse response) throws IOException {
    HttpStatusCode statusCode = response.getStatusCode();

    if (statusCode.equals(HttpStatus.BAD_REQUEST)) {
      IntegrationErrorResponseDto dto = objectMapper.readValue(response.getBody(), IntegrationErrorResponseDto.class);
      throw new IntegrationException(dto.localReason(), dto.message());
    } else if (statusCode.is4xxClientError()) {
      throw new HttpClientErrorException(response.getStatusCode(), response.getStatusText());
    } else if (statusCode.is5xxServerError()) {
      throw new HttpServerErrorException(response.getStatusCode(), response.getStatusText());
    }
  }
}
