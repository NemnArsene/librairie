package com.apilibrairie.serviceImplement;

import org.springframework.stereotype.Service;

import com.apilibrairie.model.Book;
import com.apilibrairie.repository.BookRepository;
import com.apilibrairie.service.BookService;

@Service
public class BookServiceImplement implements BookService {

    private BookRepository bookRepository;

    public BookServiceImplement(BookRepository bookRepository) {
        super();
        this.bookRepository = bookRepository;
    }

    @Override
    public Book saveBook(Book book) {
        return bookRepository.save(book);
    }

}
