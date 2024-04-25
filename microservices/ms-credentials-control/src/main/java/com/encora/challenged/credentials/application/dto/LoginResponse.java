package com.encora.challenged.credentials.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LoginResponse {
  private String clusterKey;
  private String token;
  private long expiresIn;
}
