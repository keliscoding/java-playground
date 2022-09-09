package io.github.zam0k.bookservice.controller;

import io.github.zam0k.bookservice.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.Date;

@RestController
@RequestMapping("book-service")
public class BookController {

    @Autowired
    private Environment environment;

    @GetMapping("/{id}/{currency}")
    private Book findBook(
            @PathVariable("id") Long id,
            @PathVariable("currency") String currency
    ) {
        return new Book(id, "Jane Doe", "Just a Book", new Date(),
                BigDecimal.valueOf(110.23), currency, "8100");
    }
}
