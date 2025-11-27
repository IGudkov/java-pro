package ru.inno.java.pro.payment.configuration.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("app.rest.clients")
public record RestClientsProperties(RestClientsConfigurationProperties product) {
}
