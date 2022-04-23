package com.codegym.service.book;

import com.codegym.model.Book;
import com.codegym.model.Category;
import com.codegym.repository.IBookRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

public class BookService implements IBookService{

    @Autowired
    private IBookRepository bookRepository;

    @Override
    public Iterable<Book> findAll() {
        return bookRepository.findAll();
    }

    @Override
    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id);
    }

    @Override
    public void save(Book book) {

        bookRepository.save(book);
    }

    @Override
    public void remove(Long id) {

        bookRepository.deleteById(id);
    }

    @Override
    public Iterable<Book> findAllByCategory(Category category) {
        return bookRepository.findAllByCategory(category);
    }
}
