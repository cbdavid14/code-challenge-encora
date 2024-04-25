package com.encora.challenged.products.mock;

import com.encora.challenged.products.domain.model.ProductModel;
import com.encora.challenged.products.infrastructure.persistence.IProductRepository;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

import java.util.List;

@Component
public class ProductRepositoryMock {

  @MockBean
  private IProductRepository repository;

  public void getAll(List<ProductModel> lst){

    Mockito.doReturn(Flux.fromIterable(lst)).when(repository).findAll();
  }

}
