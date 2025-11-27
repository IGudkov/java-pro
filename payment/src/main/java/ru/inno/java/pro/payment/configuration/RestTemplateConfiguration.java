package ru.inno.java.pro.payment.configuration;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import ru.inno.java.pro.payment.configuration.properties.RestClientsConfigurationProperties;
import ru.inno.java.pro.payment.configuration.properties.RestClientsProperties;

@Configuration
@EnableConfigurationProperties(RestClientsProperties.class)
public class RestTemplateConfiguration {

  @Bean
  @Qualifier("productRestTemplate")
  public RestTemplate productRestTemplate(RestClientsProperties restClientsProperties,
                                          RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
    RestClientsConfigurationProperties properties = restClientsProperties.product();
    return new RestTemplateBuilder().rootUri(properties.baseUrl())
        .requestFactory(() -> getClientHttpRequestFactory(properties))
        .errorHandler(restTemplateResponseErrorHandler)
        .build();
  }

  private ClientHttpRequestFactory getClientHttpRequestFactory(RestClientsConfigurationProperties properties) {
    HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
    clientHttpRequestFactory.setConnectTimeout(properties.connectTimeout());
    clientHttpRequestFactory.setConnectionRequestTimeout(properties.connectionRequestTimeout());
    clientHttpRequestFactory.setReadTimeout(properties.readTimeout());
    return clientHttpRequestFactory;
  }
}
