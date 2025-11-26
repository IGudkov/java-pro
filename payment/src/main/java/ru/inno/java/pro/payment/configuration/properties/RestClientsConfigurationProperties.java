package ru.inno.java.pro.payment.configuration.properties;

import java.time.Duration;

public record RestClientsConfigurationProperties(String baseUrl,
                                                 Duration connectTimeout,
                                                 Duration connectionRequestTimeout,
                                                 Duration readTimeout) {
}
