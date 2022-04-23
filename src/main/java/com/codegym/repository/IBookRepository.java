package com.codegym.repository;

import com.codegym.model.Book;
import com.codegym.model.Category;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBookRepository extends PagingAndSortingRepository<Book, Long> {
    Iterable<Book> findAllByCategory(Category category);
}
