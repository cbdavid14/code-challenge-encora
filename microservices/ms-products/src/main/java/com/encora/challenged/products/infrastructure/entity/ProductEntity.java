package com.encora.challenged.products.infrastructure.entity;

import jdk.jfr.DataAmount;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import static java.lang.String.format;

@Document(collection = "products")
@Data
@AllArgsConstructor
public class ProductEntity {

  @Id
  private String id;
  @Indexed(unique = true)
  private String name;
  private String description;
  @Field("document_number")
  private String documentNumber;
  private String state;
}
