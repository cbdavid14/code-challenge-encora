package com.encora.challenged.credentials.domain.service;

import com.encora.challenged.credentials.application.dto.LoginResponse;
import com.encora.challenged.credentials.domain.model.CredentialModel;

public interface ICredentialService {
  CredentialModel signUp(String clusterKey, String user, String password);
  LoginResponse login(String clusterKey,String user, String password);
}
