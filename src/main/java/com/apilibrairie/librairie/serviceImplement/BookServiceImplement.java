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
        // verification de l'existance de l'attribut Isbn dans la bd
        if (bookRepository.findByIsbn(book.getIsbn()) != null) {

            // s'il existe renvois le message d'erreur
            throw new ResourceNotFoundException("A book with this ISBN already exists in the database.");
        } else {

            // s'il existe applique la methode saveBook
            return bookRepository.save(book);
        }
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

        // verification du livre a modifier dans la db
        Book existingBook = bookRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Book", "Id", id));

        existingBook.setAuthor(book.getAuthor());
        existingBook.setTitle(book.getTitle());
        existingBook.setPublicationYear(book.getPublicationYear());
        existingBook.setIsbn(book.getIsbn());

        // application de la methode saveBook
        bookRepository.save(existingBook);

        return existingBook;
    }

    @Override
    public void deleteBook(Long id) {
        bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book", "Id", id));
        bookRepository.deleteById(id);
    }

}
