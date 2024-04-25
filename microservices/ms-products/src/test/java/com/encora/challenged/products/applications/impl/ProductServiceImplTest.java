package com.encora.challenged.products.applications.impl;

import com.encora.challenged.products.ProductsApplicationTests;
import com.encora.challenged.products.domain.model.ProductModel;
import com.encora.challenged.products.infrastructure.persistence.IProductRepository;
import com.encora.challenged.products.mock.ProductRepositoryMock;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.core.publisher.Flux;

import java.util.List;

import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ProductServiceImplTest extends ProductsApplicationTests {

  @Autowired
  private ProductRepositoryMock repositoryMock;

  @Autowired
  private ProductServiceImpl service;

  @Test
  void getAll() {
    ProductModel m1 = new ProductModel("1", "Product 1", "Decripcion", "1033-33", true);
    ProductModel m2 = new ProductModel("1", "Product 1", "Decripcion", "1033-33", true);
    List<ProductModel> lst = List.of(m1,m2);

    repositoryMock.getAll(lst);

    Flux<List<ProductModel>> expected = service.getAll();
    assert expected != null;
  }

  @Test
  void get() {
  }

  @Test
  void save() {
  }

  @Test
  void delete() {
  }
}
