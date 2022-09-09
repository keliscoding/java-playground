package io.github.zam0k.bookservice.repository;

import io.github.zam0k.bookservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
