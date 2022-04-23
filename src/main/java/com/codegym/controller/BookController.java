package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.model.Category;
import com.codegym.service.book.IBookService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;
@Controller
public class BookController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private ICategoryService categoryService;

    @ModelAttribute("category")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

    @GetMapping("/create-book")
    public ModelAndView showCreateBookForm(){
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book",new Book());
        return modelAndView;
    }

    @PostMapping("/create-book")
    public ModelAndView saveBook(@ModelAttribute("book") Book book) {
        bookService.save(book);
        ModelAndView modelAndView = new ModelAndView("/book/create");
        modelAndView.addObject("book", new Book());
        modelAndView.addObject("message", "New book created successfully");
        return modelAndView;
    }

    @GetMapping("/books")
    public ModelAndView listBook() {
        Iterable<Book> books = bookService.findAll();
        ModelAndView modelAndView = new ModelAndView("/book/list");
        modelAndView.addObject("books", books);
        return modelAndView;
    }

    @GetMapping("/edit-book/{id}")
    public ModelAndView showEditForm(@PathVariable Long id) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/book/edit");
            modelAndView.addObject("book", book.get());
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/edit-book")
    public ModelAndView updateCustomer(@ModelAttribute("book") Book book) {
        bookService.save(book);
        ModelAndView modelAndView = new ModelAndView("/book/edit");
        modelAndView.addObject("book", book);
        modelAndView.addObject("message", "Book updated successfully");
        return modelAndView;
    }

    @GetMapping("/delete-book/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id) {
        Optional<Book> book = bookService.findById(id);
        if (book.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/book/delete");
            modelAndView.addObject("book", book.get());
            return modelAndView;

        } else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/delete-book")
    public String deleteCustomer(@ModelAttribute("book") Book book) {
        bookService.remove(book.getId());
        return "redirect:books";
    }
}

