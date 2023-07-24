package com.apilibrairie.librairie.serviceImplement;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.apilibrairie.librairie.exception.ResourceNotFoundException;
import com.apilibrairie.librairie.model.Book;
import com.apilibrairie.librairie.repository.BookRepository;
import com.apilibrairie.librairie.service.BookService;

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

    @Override
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book getBookById(long id) {
        Optional<Book> book = bookRepository.findById(id);
        if (book.isPresent()) {
            return book.get();
        } else {
            throw new ResourceNotFoundException("Book", "Id", id);
        }

        // return bookRepository.findById(id).orElseThrow(()->
        // new ResourceNotFoundException("Book", "Id", id));
    }

    @Override
    public Book updateBook(Book book, long id) {
        // we need to check whether book with given id is exist in DB or not
        Book existingBook = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book", "Id", id));

        existingBook.setAuthor(book.getAuthor());
        existingBook.setTitle(book.getTitle());
        existingBook.setPublicationYear(book.getPublicationYear());
        existingBook.setIsbn(book.getIsbn());

        // save exiting book to Db
        bookRepository.save(existingBook);

        return existingBook;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "Id", id));
        bookRepository.deleteById(id);
    }

}
