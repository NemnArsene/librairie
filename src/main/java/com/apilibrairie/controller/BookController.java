package com.apilibrairie.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apilibrairie.model.Book;
import com.apilibrairie.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private BookService BookService;

    public BookController(com.apilibrairie.service.BookService bookService) {
        BookService = bookService;
    }

    // build create book api rest
    @PostMapping()
    public ResponseEntity<Book> saveBook(@RequestBody Book book) {
        return new ResponseEntity<Book>(BookService.saveBook(book), HttpStatus.CREATED);
    }
}
