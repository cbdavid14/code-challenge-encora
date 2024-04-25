package com.encora.challenged.products.applications.handler;

import com.encora.challenged.http.exceptions.NotFoundException;
import com.encora.challenged.products.applications.impl.ProductMapper;
import com.encora.challenged.products.applications.impl.ProductServiceImpl;
import com.encora.challenged.products.domain.model.ProductModel;
import com.encora.challenged.products.domain.service.IProductService;
import com.encora.challenged.products.infrastructure.persistence.IProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static java.util.logging.Level.FINE;
import static org.springframework.web.reactive.function.BodyInserters.fromValue;

@Component
public class ProductHandler {
  private static final Logger LOG = LoggerFactory.getLogger(ProductServiceImpl.class);
  @Autowired
  private IProductRepository productRepository;
  @Autowired
  private ProductMapper productMapper;

  public Mono<ServerResponse> getAllProducts(ServerRequest serverRequest) {
    Flux<ProductModel> map = productRepository.findAll()
      .delayElements(Duration.ofSeconds(1))
      .log(LOG.getName(), FINE)
      .map(e -> productMapper.entityToModel(e));

    return ServerResponse.ok()
      .contentType(MediaType.TEXT_EVENT_STREAM)
      .body(map, ProductModel.class);
  }

  public Mono<ServerResponse> getProduct(ServerRequest serverRequest) {
    String id = serverRequest.pathVariable("id");
    Mono<ProductModel> itemMono = productRepository.findById(id)
      .switchIfEmpty(Mono.error(new NotFoundException("No product found for productId: " + id)))
      .log(LOG.getName(), FINE)
      .map(e -> productMapper.entityToModel(e));

    return itemMono.flatMap(item ->
      ServerResponse.ok()
        .contentType(MediaType.APPLICATION_JSON)
        .body(fromValue(item)));

  }

  public Mono<ServerResponse> createProduct(ServerRequest serverRequest) {
    Mono<ProductModel> product = serverRequest.bodyToMono(ProductModel.class);


    return null;

  }

  /*

      public Mono<ServerResponse> createBook(ServerRequest serverRequest) {
          Mono<Book> bookMono = serverRequest.bodyToMono(Book.class);

          return bookMono.flatMap(book ->
             ServerResponse.status(HttpStatus.CREATED)
                     .contentType(MediaType.APPLICATION_JSON)
                     .body(bookRepository.save(book), Book.class));

      }

      public Mono<ServerResponse> deleteBook(ServerRequest serverRequest) {
          String id = serverRequest.pathVariable("id");
          Mono<Void> deletedBook = bookRepository.deleteById(Long.valueOf(id));
          return ServerResponse.ok()
                  .contentType(MediaType.APPLICATION_JSON)
                  .body(deletedBook, Void.class);

      }

      public Mono<ServerResponse> updateBook(ServerRequest serverRequest) {
          String id = serverRequest.pathVariable("id");
          Mono<Book> updatedBook = serverRequest.bodyToMono(Book.class).log("mono: ")
                  .flatMap(book -> bookRepository.findById(Long.valueOf(id)).log()
                          .flatMap(oldBook -> {
                              oldBook.setTitle(book.getTitle());
                              oldBook.setAuthor(book.getAuthor());
                              return bookRepository.save(oldBook).log();
                          }));

          return updatedBook.flatMap(book -> ServerResponse.ok()
                  .contentType(MediaType.APPLICATION_JSON)
                  .body(fromValue(book)))
                  .switchIfEmpty(notFound);
      }
  */
}
