package ru.inno.java.pro.model.dto;

import java.math.BigDecimal;

public record PaymentRequestDto(Long productId,
                                BigDecimal amount) {
}
