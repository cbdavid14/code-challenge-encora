package com.encora.challenged.customer.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginDto {
  private String clusterKey;
  private String token;
  private long expiresIn;
}
