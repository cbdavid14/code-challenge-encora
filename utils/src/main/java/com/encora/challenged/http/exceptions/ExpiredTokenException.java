package com.encora.challenged.http.exceptions;

public class ExpiredTokenException extends RuntimeException{
  public ExpiredTokenException() {}

  public ExpiredTokenException(String message) {
    super(message);
  }

  public ExpiredTokenException(String message, Throwable cause) {
    super(message, cause);
  }

  public ExpiredTokenException(Throwable cause) {
    super(cause);
  }
}
