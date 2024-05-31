package com.jam.spring.basic.repositories;

import com.jam.spring.basic.domain.Publisher;
import org.springframework.data.repository.CrudRepository;

public interface PublisherRepository extends CrudRepository<Publisher, Long> {
}
