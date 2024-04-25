package com.encora.challenged.credentials.application.controller;

import com.encora.challenged.credentials.application.dto.*;
import com.encora.challenged.credentials.domain.model.CredentialModel;
import com.encora.challenged.credentials.domain.service.ICredentialService;
import com.encora.challenged.credentials.infrastructure.entity.CredentialEntity;
import com.encora.challenged.http.constants.ErrorMessage;
import com.encora.challenged.http.exceptions.BadCredentialException;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class CredentialController {
  private static final Logger log = LoggerFactory.getLogger(CredentialController.class);
  @Autowired
  private ICredentialService credentialControl;

  @Autowired
  private IResponseMapper responseMapper;

  @PostMapping("/sign-up")
  public ResponseEntity<SignupResponse> signUp(@RequestBody SignupRequest req) {
    CredentialModel model = credentialControl.signUp(req.getClusterKey(), req.getUser(), req.getPassword());

    return ResponseEntity.ok(responseMapper.entityToApi(model));
  }

  @PostMapping("/login")
  public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest req) {
    LoginResponse login = credentialControl.login(req.getClusterKey(), req.getUser(), req.getPassword());

    return ResponseEntity.ok(login);
  }

  @GetMapping("/token")
  public ResponseEntity<CredentialResponse> token(HttpServletRequest request) {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    if (authentication == null) {
      throw new BadCredentialException(ErrorMessage.BAD_CREDENTIALS.getText());
    }

    CredentialEntity currentUser = (CredentialEntity) authentication.getPrincipal();
    long tokenExpireIn = (long) request.getAttribute("tokenExpireIn");

    CredentialResponse resp = new CredentialResponse();
    resp.setUser(currentUser.getUser());
    resp.setClusterKey(currentUser.getClusterKey());
    resp.setPassword(currentUser.getPassword());
    resp.setExpiresIn(tokenExpireIn);
    return ResponseEntity.ok(resp);
  }
}
