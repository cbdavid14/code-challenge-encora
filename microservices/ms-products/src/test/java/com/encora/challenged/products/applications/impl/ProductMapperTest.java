package com.encora.challenged.products.applications.impl;

import com.encora.challenged.products.ProductsApplicationTests;
import com.encora.challenged.products.domain.model.ProductModel;
import com.encora.challenged.products.infrastructure.entity.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class ProductMapperTest extends ProductsApplicationTests {

  @Autowired
  private Parser parser;

  @Autowired
  private ProductMapper mapper;

  @Test
  void mapperEntityToModelTests() {
    ProductEntity entity = new ProductEntity("1", "Product", "Description", "123-234", "ACTIVE");

    ProductModel model = mapper.entityToModel(entity);

    assertEquals(entity.getId(), model.getId());
    assertEquals(entity.getName(), model.getName());
    assertEquals(entity.getDescription(), model.getDescription());
    assertEquals(entity.getDocumentNumber(), model.getDocumentNumber());
    assertEquals(entity.getState(), "ACTIVE");
    assertEquals(model.getState(), true);
  }

  @Test
  void mapperModelToEntityTests() {
    ProductModel model = new ProductModel("1", "Product", "Description", "123-234", false);

    ProductEntity entity = mapper.modelToEntity(model);

    assertEquals(entity.getId(), model.getId());
    assertEquals(entity.getName(), model.getName());
    assertEquals(entity.getDescription(), model.getDescription());
    assertEquals(entity.getDocumentNumber(), model.getDocumentNumber());
    assertEquals(entity.getState(), "INACTIVE");
    assertEquals(model.getState(), false);
  }
}
