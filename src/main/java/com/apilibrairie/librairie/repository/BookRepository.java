package com.apilibrairie.librairie.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.apilibrairie.librairie.model.Book;

//class BookDao
public interface BookRepository extends JpaRepository<Book, Long> {

}
