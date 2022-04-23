package com.codegym.service.book;

import com.codegym.model.Book;
import com.codegym.model.Category;
import com.codegym.service.IGeneralService;

public interface IBookService extends IGeneralService<Book> {
    Iterable<Book> findAllByCategory(Category category);
}
