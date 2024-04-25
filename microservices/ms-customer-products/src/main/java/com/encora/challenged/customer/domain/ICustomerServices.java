package com.encora.challenged.customer.domain;

import com.encora.challenged.customer.application.dto.CredentialDto;
import com.encora.challenged.customer.application.dto.LoginDto;
import com.encora.challenged.customer.application.dto.ProductDto;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

public interface ICustomerServices {
  LoginDto getCredentialToken(CredentialDto req);
  Mono<ProductDto> getProductById(String productID, String token);
  Mono<ProductDto> createProduct(ProductDto product, String token);
  void deleteProduct(String productID, String token);
}
