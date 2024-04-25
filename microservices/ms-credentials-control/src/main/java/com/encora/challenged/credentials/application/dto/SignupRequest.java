package com.encora.challenged.credentials.application.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SignupRequest {
  private String clusterKey;
  private String user;
  private String password;
}
