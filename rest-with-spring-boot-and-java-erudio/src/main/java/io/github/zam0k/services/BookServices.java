package io.github.zam0k.services;

import io.github.zam0k.controllers.BookController;
import io.github.zam0k.data.vo.v1.BookVO;
import io.github.zam0k.exceptions.ResourceNotFoundException;
import io.github.zam0k.mapper.DozerMapper;
import io.github.zam0k.model.Book;
import io.github.zam0k.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    private BookRepository repository;

    public List<BookVO> findAll() {
        logger.info("Searching for all books...");
        List<BookVO> books = DozerMapper.parseListObjects(repository.findAll(), BookVO.class);
        books.stream()
                .forEach(bookVO ->
                        bookVO.add(
                                linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel())
                        );
        return books;
    }


    public BookVO findById(Long id) {
        logger.info("Searching for a book by its id...");
        Book book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        BookVO bookVO = DozerMapper.parseObject(book, BookVO.class);
        bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
        return bookVO;
    }

    public BookVO create(BookVO vo) {
        logger.info("Creating a new book...");
        Book book = DozerMapper.parseObject(vo, Book.class);
        BookVO bookVO = DozerMapper.parseObject(repository.save(book), BookVO.class);
        bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
        return bookVO;
    }

    public BookVO update(Long id, BookVO vo) {
        logger.info("Updating the book...");
        Book book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        book.setAuthor(vo.getAuthor());
        book.setLaunchDate(vo.getLaunchDate());
        book.setPrice(vo.getPrice());
        book.setTitle(vo.getTitle());

        repository.save(book);
        BookVO bookVO = DozerMapper.parseObject(book, BookVO.class);
        bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel());
        return bookVO;
    }

    public void delete(Long id) {
        logger.info("Deleting the book...");
        Book book = repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("No records found for this ID!"));
        repository.delete(book);
    }
}
