package com.encora.challenged.http.constants;

public enum ErrorMessage {
  EXPIRED_TOKEN("Token has expired"),
  SIGNATURE_INVALID("The JWT signature is invalid"),
  ACCESS_DENIED("You are not authorized to access this resource"),
  BAD_CREDENTIALS("The username or password is incorrect");

  private final String text;

  ErrorMessage(String text) {
    this.text = text;
  }

  public String getText() {
    return text;
  }
}
