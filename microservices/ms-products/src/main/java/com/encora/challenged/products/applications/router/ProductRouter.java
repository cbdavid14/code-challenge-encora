package com.encora.challenged.products.applications.router;


import com.encora.challenged.products.applications.handler.ProductHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

import static org.springframework.web.reactive.function.server.RequestPredicates.*;

@Configuration
public class ProductRouter {

  @Bean
  public RouterFunction<ServerResponse> bookRouterFunc(ProductHandler productHandler) {
    return RouterFunctions
      .route(GET("/api/products/").and(accept(MediaType.TEXT_EVENT_STREAM)), productHandler::getAllProducts)
      .andRoute(GET("/api/products/" + "{id}").and(accept(MediaType.APPLICATION_JSON)), productHandler::getProduct)
      .andRoute(POST("/api/products/").and(accept(MediaType.APPLICATION_JSON)), productHandler::createProduct);
  }

}
