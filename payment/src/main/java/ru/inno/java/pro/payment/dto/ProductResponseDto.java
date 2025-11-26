package ru.inno.java.pro.payment.dto;

import java.math.BigDecimal;

public record ProductResponseDto(Long id,
                                 String accountNumber,
                                 BigDecimal balance,
                                 String productType,
                                 Long userId) {
}
