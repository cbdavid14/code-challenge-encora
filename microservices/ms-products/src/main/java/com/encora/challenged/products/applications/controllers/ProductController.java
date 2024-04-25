package com.encora.challenged.products.applications.controllers;

import com.encora.challenged.products.domain.model.ProductModel;
import com.encora.challenged.products.domain.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/api")
class ProductController {

  @Autowired
  private IProductService productService;

  @GetMapping(value = "/products", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
  public Flux<List<ProductModel>> getAll() {
    return productService.getAll().delayElements(Duration.ofSeconds(1));
  }

  @GetMapping("/products/{productId}")
  public Mono<ProductModel> get(@PathVariable(value = "productId") String id) {
    return productService.get(id);
  }

  @PostMapping("/products")
  public Mono<ProductModel> save(@RequestBody ProductModel product) {
    return productService.save(product).log();
  }

  @DeleteMapping("/products/{productId}")
  public Mono<ResponseEntity<Void>> delete(@PathVariable(value = "productId") String id) {

    return productService.delete(id)
      .map(r -> ResponseEntity.ok().<Void>build());
  }
}
