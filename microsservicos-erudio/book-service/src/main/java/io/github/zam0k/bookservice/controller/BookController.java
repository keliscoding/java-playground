package io.github.zam0k.bookservice.controller;

import io.github.zam0k.bookservice.model.Book;
import io.github.zam0k.bookservice.proxy.CambioProxy;
import io.github.zam0k.bookservice.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Book Endpoint")
@RestController
@RequestMapping("book-service")
public class BookController {

  @Autowired private Environment environment;

  @Autowired private BookRepository repository;

  @Autowired private CambioProxy cambioProxy;

  @Operation(summary = "Find a specific book by its id")
  @GetMapping("/{id}/{currency}")
  public Book findBook(@PathVariable("id") Long id, @PathVariable("currency") String currency) {

    var book = repository.findById(id).orElseThrow(() -> new RuntimeException("Book not found"));

    var port = environment.getProperty("local.server.port");

    var cambio = cambioProxy.getCambio(book.getPrice(), "USD", currency);

    book.setEnvironment("Book port: "+ port + " Cambio port: " + cambio.getEnvironment());
    book.setPrice(cambio.getConvertedValue());

    return book;
  }
}
