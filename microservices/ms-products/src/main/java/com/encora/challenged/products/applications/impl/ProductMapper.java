package com.encora.challenged.products.applications.impl;

import com.encora.challenged.products.domain.model.ProductModel;
import com.encora.challenged.products.infrastructure.entity.ProductEntity;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = Parser.class)
public interface ProductMapper {

  ProductModel entityToModel(ProductEntity entity);
  ProductEntity modelToEntity(ProductModel model);
}
