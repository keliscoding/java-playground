package io.github.zam0k.repositories;

import io.github.zam0k.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<Book, Long> {
}
