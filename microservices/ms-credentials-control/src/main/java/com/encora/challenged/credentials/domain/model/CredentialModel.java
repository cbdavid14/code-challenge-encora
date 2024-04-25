package com.encora.challenged.credentials.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

@Data
@Component
public class CredentialModel {
  private int id;
  private String clusterKey;
  private String user;
  private String password;
}
