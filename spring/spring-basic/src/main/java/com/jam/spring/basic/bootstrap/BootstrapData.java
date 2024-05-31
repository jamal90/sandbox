package com.jam.spring.basic.bootstrap;

import com.jam.spring.basic.domain.Author;
import com.jam.spring.basic.domain.Book;
import com.jam.spring.basic.domain.Publisher;
import com.jam.spring.basic.repositories.AuthorRepository;
import com.jam.spring.basic.repositories.BookRepository;
import com.jam.spring.basic.repositories.PublisherRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootstrapData implements CommandLineRunner {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final PublisherRepository publisherRepository;

    public BootstrapData(AuthorRepository authorRepository, BookRepository bookRepository, PublisherRepository publisherRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.publisherRepository = publisherRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        Author jj = new Author("Jamal", "Jalal");
        Book book1 = new Book("book1", "131231");

        Publisher pub1 = new Publisher("jPub", "somewhere");
        publisherRepository.save(pub1);

        jj.getBooks().add(book1);
        book1.getAuthors().add(jj);

        book1.setPublisher(pub1);
        pub1.getBooks().add(book1);

        authorRepository.save(jj);
        bookRepository.save(book1);

        System.out.println("bootstrapped!");
        System.out.println("No. of Books: " + bookRepository.count());
        System.out.println("No. of Authors: " + authorRepository.count());
        System.out.println("No. of Publishers: " + publisherRepository.count());
    }
}
