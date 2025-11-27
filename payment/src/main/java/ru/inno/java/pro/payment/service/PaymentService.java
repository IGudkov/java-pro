package ru.inno.java.pro.payment.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.inno.java.pro.payment.dto.PaymentRequestDto;
import ru.inno.java.pro.payment.dto.PaymentResponseDto;
import ru.inno.java.pro.payment.dto.ProductResponseDto;
import ru.inno.java.pro.payment.utils.ObjectMapperUtils;

import java.util.List;

@Service
public class PaymentService {
  private final RestTemplate productRestTemplate;

  private final ObjectMapperUtils objectMapperUtils;

  @Value("${app.rest.clients.product.products-by-user-id-url}")
  private String productsByUserIdUrl;

  @Value("${app.rest.clients.product.execute-payment-url}")
  private String executePaymentUrl;

  public PaymentService(RestTemplate productRestTemplate,
                        ObjectMapperUtils objectMapperUtils) {
    this.productRestTemplate = productRestTemplate;
    this.objectMapperUtils = objectMapperUtils;
  }

  public List<ProductResponseDto> getProductsByUserId(Long userId) throws JsonProcessingException {
    String response = productRestTemplate.getForObject(productsByUserIdUrl, String.class, userId);
    return objectMapperUtils.toList(response, ProductResponseDto.class);
  }

  public PaymentResponseDto executePayment(PaymentRequestDto paymentRequest) throws JsonProcessingException {
    String response = productRestTemplate.postForObject(executePaymentUrl, paymentRequest, String.class);
    return objectMapperUtils.getObjectMapper()
        .readValue(response, PaymentResponseDto.class);
  }

}
