package com.apilibrairie.librairie.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apilibrairie.librairie.model.Book;

import com.apilibrairie.librairie.service.BookService;

@RestController
@RequestMapping(path = "/api/books")
public class BookController {

    private BookService BookService;

    public BookController(com.apilibrairie.librairie.service.BookService bookService) {
        BookService = bookService;
    }

    // build create book apiRest
    @PostMapping()
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return new ResponseEntity<Book>(BookService.saveBook(book), HttpStatus.CREATED);
    }

    // build get all books apiRest
    @GetMapping
    public List<Book> getAllBooks() {
        return BookService.getAllBooks();
    }

    // buid get book by id
    // http://localhost:8080/api/books/1
    @GetMapping("{id}")
    public ResponseEntity<Book> getBookEntityById(@PathVariable("id") long bookId) {
        return new ResponseEntity<Book>(BookService.getBookById(bookId), HttpStatus.OK);

    }

    // build update book apiRest
    // http://localhost:8080/api/books/1
    @PutMapping("{id}")
    public ResponseEntity<Book> updateBook(@PathVariable("id") long id, @RequestBody Book book) {
        return new ResponseEntity<Book>(BookService.updateBook(book, id), HttpStatus.OK);

    }
    // build delete book apiRest
    // http://localhost:8080/api/books/1

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteBook(@PathVariable("id") long id) {
        BookService.deleteBook(id);
        return new ResponseEntity<String>("Books delete successfully!", HttpStatus.OK);

    }

}
