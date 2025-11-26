package ru.inno.java.pro.payment.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.inno.java.pro.payment.dto.PaymentRequestDto;
import ru.inno.java.pro.payment.dto.PaymentResponseDto;
import ru.inno.java.pro.payment.dto.ProductResponseDto;
import ru.inno.java.pro.payment.service.PaymentService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/payments")
public class PaymentController {
  private final PaymentService paymentService;

  public PaymentController(PaymentService paymentService) {
    this.paymentService = paymentService;
  }

  @GetMapping(value = "/products/user-id/{userId}")
  public List<ProductResponseDto> getProductsByUserId(@PathVariable Long userId) throws JsonProcessingException {
    return paymentService.getProductsByUserId(userId);
  }

  @PostMapping(value = "/execute")
  public PaymentResponseDto executePayment(@RequestBody PaymentRequestDto paymentRequest) throws JsonProcessingException {
    return paymentService.executePayment(paymentRequest);
  }

}
