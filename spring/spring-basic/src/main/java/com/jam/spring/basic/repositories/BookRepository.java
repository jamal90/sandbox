package com.jam.spring.basic.repositories;

import com.jam.spring.basic.domain.Book;
import org.springframework.data.repository.CrudRepository;

public interface BookRepository extends CrudRepository<Book, Long> {
}
