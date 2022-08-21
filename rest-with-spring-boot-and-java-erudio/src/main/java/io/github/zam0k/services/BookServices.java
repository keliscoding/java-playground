package io.github.zam0k.services;

import io.github.zam0k.controllers.BookController;
import io.github.zam0k.data.vo.v1.BookVO;
import io.github.zam0k.exceptions.ResourceNotFoundException;
import io.github.zam0k.mapper.DozerMapper;
import io.github.zam0k.model.Book;
import io.github.zam0k.repositories.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Service
public class BookServices {

    private Logger logger = Logger.getLogger(BookServices.class.getName());

    @Autowired
    private BookRepository repository;

    @Autowired
    PagedResourcesAssembler<BookVO> assembler;

    public PagedModel<EntityModel<BookVO>> findAll(Pageable pageable) {
        logger.info("Searching for all books...");

        //acha todos os livros
        Page<Book> bookPage = repository.findAll(pageable);

        //parseia os livros pra livrosVO
        Page<BookVO> bookVOPage = bookPage.map(entity -> DozerMapper.parseObject(entity, BookVO.class));

        //adiciona o hateoas para cada livro individualmente
        bookVOPage.map(bookVO ->
                bookVO.add(linkTo(methodOn(BookController.class).findById(bookVO.getKey())).withSelfRel()));


        //cria o link hateoas para pagina
        Link link = linkTo(methodOn(BookController.class).findAll(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                "asc")).withSelfRel();

        //adiciona o link hateoas na pagina
        return assembler.toModel(bookVOPage, link);
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
