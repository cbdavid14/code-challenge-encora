package com.encora.challenged.customer.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProductDto {
  private String id;
  private String name;
  private String description;
  @JsonProperty("document_number")
  private String documentNumber;
  private Boolean state;
}
