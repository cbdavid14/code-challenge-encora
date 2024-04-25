package com.encora.challenged.products.domain.service;

import com.encora.challenged.products.domain.model.ProductModel;
import com.encora.challenged.products.infrastructure.entity.ProductEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface IProductService {
  Flux<List<ProductModel>> getAll();
  Mono<ProductModel> get(String id);
  Mono<ProductModel> save(ProductModel product);
  Mono<Void> delete (String id);
}
