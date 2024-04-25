package com.encora.challenged.products.infrastructure.persistence;

import com.encora.challenged.products.infrastructure.entity.ProductEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface IProductRepository extends ReactiveCrudRepository<ProductEntity, String> {
  Mono<ProductEntity> findById(int productId);
}
