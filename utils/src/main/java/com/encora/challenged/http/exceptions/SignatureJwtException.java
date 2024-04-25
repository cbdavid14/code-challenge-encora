package com.encora.challenged.http.exceptions;

public class SignatureJwtException extends RuntimeException {
  public SignatureJwtException() {}

  public SignatureJwtException(String message) {
    super(message);
  }

  public SignatureJwtException(String message, Throwable cause) {
    super(message, cause);
  }

  public SignatureJwtException(Throwable cause) {
    super(cause);
  }
}
