package ru.inno.java.pro.payment.dto;

import java.math.BigDecimal;

public record PaymentRequestDto(Long productId,
                                BigDecimal amount) {
}
