package com.encora.challenged.customer.application.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class BuilderClient {
  private static final Logger log = LoggerFactory.getLogger(BuilderClient.class);

  @Value("${url.product-service.host}")
  private String baseUrlProduct;

  @Value("${url.product-service.port}")
  private String basePortProduct;

  @Value("${url.credentials-service.host}")
  private String baseUrlCredential;

  @Value("${url.credentials-service.port}")
  private String basePortCredential;

  @Bean("productWebClient")
  public WebClient productWebClient() {
    String uri = baseUrlProduct + ":" + basePortProduct;
    log.info("Product Web Client: " + uri);
    return WebClient.builder()
      .baseUrl(uri)
      .build();
  }

  @Bean("credentialWebClient")
  public WebClient credentialWebClient() {
    String uri = baseUrlCredential + ":" + basePortCredential;
    log.info("Credential Web Client: " + uri);
    return WebClient.builder()
      .baseUrl(uri)
      .build();
  }

}
