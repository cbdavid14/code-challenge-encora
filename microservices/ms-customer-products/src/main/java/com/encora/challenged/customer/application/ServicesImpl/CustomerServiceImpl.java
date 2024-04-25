package com.encora.challenged.customer.application.ServicesImpl;

import com.encora.challenged.customer.application.client.BuilderClient;
import com.encora.challenged.customer.application.dto.CredentialDto;
import com.encora.challenged.customer.application.dto.LoginDto;
import com.encora.challenged.customer.application.dto.ProductDto;
import com.encora.challenged.customer.domain.ICustomerServices;
import com.encora.challenged.http.constants.ErrorMessage;
import com.encora.challenged.http.exceptions.SignatureJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements ICustomerServices {

  private static final Logger log = LoggerFactory.getLogger(CustomerServiceImpl.class);

  @Qualifier("credentialWebClient")
  @Autowired
  private WebClient credentialWebClient;

  @Qualifier("productWebClient")
  @Autowired
  private WebClient productWebClient;

  @Override
  public LoginDto getCredentialToken(CredentialDto req) {
    String url = "/auth/login";

    LoginDto loginDto = credentialWebClient
      .post()
      .uri(url)
      .bodyValue(req)
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(LoginDto.class)
      .block();

    return loginDto;
  }

  @Override
  public Mono<ProductDto> getProductById(String productID, String token) {
    String uri = "/api/products/" + productID;
    return productWebClient.get()
      .uri(uri)
      .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(ProductDto.class);
  }

  @Override
  public Mono<ProductDto> createProduct(ProductDto product, String token) {
    String uri = "/api/products";
    return productWebClient.post()
      .uri(uri)
      .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
      .bodyValue(product)
      .accept(MediaType.APPLICATION_JSON)
      .retrieve()
      .bodyToMono(ProductDto.class);
  }

  @Override
  public void deleteProduct(String productID, String token) {
    String uri = "/api/products/" + productID;
    productWebClient.delete()
      .uri(uri)
      .header(HttpHeaders.AUTHORIZATION, "Bearer " + token)
      .accept(MediaType.APPLICATION_JSON)
      .retrieve();
  }
}
