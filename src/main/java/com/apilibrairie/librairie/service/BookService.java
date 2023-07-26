package com.apilibrairie.librairie.service;

import java.util.List;

import com.apilibrairie.librairie.model.Book;

public interface BookService {

    // save book
    Book saveBook(Book book);

    // list all books
    List<Book> getAllBooks();

    // get book by id
    Book getBookById(long id);

    // delete book
    void deleteBook(Long id);

    // updateBook
    Book updateBook(Book book, long id);

}
