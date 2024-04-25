package com.encora.challenged.products.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
@AllArgsConstructor
public class ProductModel implements Serializable {
  private String id;
  private String name;
  private String description;
  @JsonProperty("document_number")
  private String documentNumber;
  private Boolean state;
}
