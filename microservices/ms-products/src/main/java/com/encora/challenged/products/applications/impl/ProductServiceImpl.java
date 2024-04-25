package com.encora.challenged.products.applications.impl;

import com.encora.challenged.http.exceptions.InvalidInputException;
import com.encora.challenged.http.exceptions.NotFoundException;
import com.encora.challenged.products.domain.model.ProductModel;
import com.encora.challenged.products.domain.service.IProductService;
import com.encora.challenged.products.infrastructure.entity.ProductEntity;
import com.encora.challenged.products.infrastructure.persistence.IProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

import static java.util.logging.Level.FINE;

@Service
public class ProductServiceImpl implements IProductService {
  private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);
  @Autowired
  private IProductRepository productRepository;

  @Autowired
  private ProductMapper productMapper;

  @Override
  public Flux<List<ProductModel>> getAll() {

    return productRepository.findAll()
      .delayElements(Duration.ofSeconds (1))
      .log(LOG.getName(), FINE)
      .map(e -> Collections.singletonList(productMapper.entityToModel(e)))
      ;
  }

  @Override
  public Mono<ProductModel> get(String id) {
    if (id.isEmpty()) {
      throw new InvalidInputException("Invalid Id: " + id);
    }
    LOG.info("Will get product info for id={}", id);

    return productRepository.findById(id)
      .switchIfEmpty(Mono.error(new NotFoundException("No product found for productId: " + id)))
      .log(LOG.getName(), FINE)
      .map(e -> productMapper.entityToModel(e));
  }

  @Override
  public Mono<ProductModel> save(ProductModel product) {
    ProductEntity entity = productMapper.modelToEntity(product);

    Mono<ProductModel> model = productRepository.save(entity)
      .log(LOG.getName(), FINE)
      .onErrorMap(
        DuplicateKeyException.class,
        ex -> new InvalidInputException("Duplicate product, Product Name: " + product.getName())
      )
      .map(e -> productMapper.entityToModel(e));

    return model;
  }

  @Override
  public Mono<Void> delete(String id) {
    if (id.isEmpty()) {
      throw new InvalidInputException("Invalid Id: " + id);
    }

    LOG.debug("deleteProduct: tries to delete an entity with productId: {}", id);

    return productRepository.findById(id)
      .switchIfEmpty(Mono.error(new NotFoundException("No product found for productId: " + id)))
      .flatMap(product -> productRepository.delete(product));
  }
}
