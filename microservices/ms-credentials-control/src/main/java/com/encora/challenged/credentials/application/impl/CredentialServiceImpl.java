package com.encora.challenged.credentials.application.impl;

import com.encora.challenged.credentials.application.dto.LoginResponse;
import com.encora.challenged.credentials.domain.model.CredentialModel;
import com.encora.challenged.credentials.domain.service.ICredentialService;
import com.encora.challenged.credentials.infrastructure.entity.CredentialEntity;
import com.encora.challenged.credentials.infrastructure.entity.IEntityMapper;
import com.encora.challenged.credentials.infrastructure.persistence.ICredentialRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class CredentialServiceImpl implements ICredentialService {

  @Autowired
  public ICredentialRepository credentialRepository;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private IEntityMapper entityMapper;

  @Autowired
  private AuthenticationManager authenticationManager;

  @Autowired
  private JwtService jwtService;

  @Override
  public CredentialModel signUp(String clusterKey, String user, String password) {
    CredentialEntity obj = new CredentialEntity();
    obj.setUser(user);
    obj.setClusterKey(clusterKey);
    obj.setPassword(passwordEncoder.encode(password));

    CredentialEntity created = credentialRepository.save(obj);

    return entityMapper.entityToModel(created);
  }

  @Override
  public LoginResponse login(String clusterKey, String user, String password) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        user + clusterKey,
        password
      )
    );

    CredentialEntity entity = credentialRepository.findByUserAndClusterKey(user, clusterKey).orElseThrow();

    Map<String, Object> map = new HashMap<>();
    map.put("clusterKey", entity.getClusterKey());
    String jwtToken = jwtService.generateToken(map, entity);

    LoginResponse loginResponse = new LoginResponse();
    loginResponse.setClusterKey(clusterKey);
    loginResponse.setToken(jwtToken);
    loginResponse.setExpiresIn(jwtService.getExpirationTimeInSeconds());

    return loginResponse;
  }

}

