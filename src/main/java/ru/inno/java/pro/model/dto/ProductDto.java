package ru.inno.java.pro.model.dto;

import ru.inno.java.pro.model.enums.ProductType;

import java.math.BigDecimal;

public record ProductDto(Long id,
                         String accountNumber,
                         BigDecimal balance,
                         ProductType productType,
                         Long userId) {
}
