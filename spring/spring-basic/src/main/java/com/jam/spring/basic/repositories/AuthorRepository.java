package com.jam.spring.basic.repositories;

import com.jam.spring.basic.domain.Author;
import org.springframework.data.repository.CrudRepository;

public interface AuthorRepository extends CrudRepository<Author, Long> {
}
