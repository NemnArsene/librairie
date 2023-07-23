package com.apilibrairie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apilibrairie.model.Book;

//class BookDao
public interface BookRepository extends JpaRepository<Book, Long> {

}
