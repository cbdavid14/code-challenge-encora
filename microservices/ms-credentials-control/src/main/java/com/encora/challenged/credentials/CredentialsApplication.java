package com.encora.challenged.credentials;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
@ComponentScan("com.encora.challenged")
public class CredentialsApplication {
  private final static Logger LOG = LoggerFactory.getLogger(CredentialsApplication.class);

  public static void main(String[] args) {
    ConfigurableApplicationContext ctx = SpringApplication.run(CredentialsApplication.class, args);

    String mysqlUri = ctx.getEnvironment().getProperty("spring.datasource.url");
    LOG.info("Connected to MySQL: " + mysqlUri);
  }
}
