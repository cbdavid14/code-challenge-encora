package com.encora.challenged.products.applications.impl;

import org.springframework.stereotype.Component;

@Component
public class Parser {

  public String asString(Boolean bool) {
    return null == bool ?
      null : (bool ?
      "ACTIVE" : "INACTIVE"
    );
  }

  public Boolean asBoolean(String bool) {
    return null == bool ?
      null : (bool.trim().toLowerCase().startsWith("active") ?
      Boolean.TRUE : Boolean.FALSE
    );
  }
}
