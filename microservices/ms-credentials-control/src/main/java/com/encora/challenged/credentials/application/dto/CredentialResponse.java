package com.encora.challenged.credentials.application.dto;

import lombok.Data;

@Data
public class CredentialResponse {
  private String clusterKey;
  private String user;
  private String password;
  private long expiresIn;
}
