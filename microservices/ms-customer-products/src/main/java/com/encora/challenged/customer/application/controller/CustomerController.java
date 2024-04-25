package com.encora.challenged.customer.application.controller;

import com.encora.challenged.customer.application.ServicesImpl.CustomerServiceImpl;
import com.encora.challenged.customer.application.dto.CredentialDto;
import com.encora.challenged.customer.application.dto.LoginDto;
import com.encora.challenged.customer.application.dto.ProductDto;
import com.encora.challenged.customer.domain.ICustomerServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
public class CustomerController {

  @Autowired
  private ICustomerServices customerService;

  @GetMapping("/credential/token")
  public ResponseEntity<LoginDto> getCredentialToken(@RequestHeader HttpHeaders headers) {
    CredentialDto req = getCredentialDto(headers);

    return ResponseEntity.ok().body(customerService.getCredentialToken(req));
  }

  @GetMapping("/product/{id}")
  public Mono<ResponseEntity<ProductDto>> getProductById(@PathVariable(value = "id") String id, @RequestHeader HttpHeaders headers) {
    String token = headers.getFirst("token");

    return customerService.getProductById(id, token)
      .map(product -> ResponseEntity.ok().body(product));
  }

  @PostMapping("/product")
  public Mono<ResponseEntity<ProductDto>> create(@RequestBody ProductDto productDto, @RequestHeader HttpHeaders headers) {

    String token = headers.getFirst("token");

    return customerService.createProduct(productDto, token)
      .map(product -> ResponseEntity.status(HttpStatus.CREATED).body(product));
  }

  @DeleteMapping("/product/{id}")
  public ResponseEntity delete(@PathVariable(value = "id") String id, @RequestHeader HttpHeaders headers) {

    String token = headers.getFirst("token");

    customerService.deleteProduct(id, token);
    return ResponseEntity.ok().build();
  }

  private static CredentialDto getCredentialDto(HttpHeaders headers) {
    CredentialDto req = new CredentialDto(
      headers.getFirst("cluster"),
      headers.getFirst("user"),
      headers.getFirst("password")
    );
    return req;
  }
}
