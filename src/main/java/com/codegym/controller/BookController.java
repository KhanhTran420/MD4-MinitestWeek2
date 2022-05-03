package com.codegym.controller;

import com.codegym.model.Book;
import com.codegym.model.Category;
import com.codegym.service.book.IBookService;
import com.codegym.service.category.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.data.domain.Pageable;


import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
@RestController
//@PropertySource("classpath:upload_file.properties")
@RequestMapping("/books")
public class BookController {

    @Autowired
    private IBookService bookService;

    @Autowired
    private ICategoryService categoryService;

//    @Value("${file-upload}")
//    private String fileUpload;

    @ModelAttribute("category")
    public Iterable<Category> categories(){
        return categoryService.findAll();
    }

//    @GetMapping("/create-book")
//    public ModelAndView showCreateBookForm(){
//        ModelAndView modelAndView = new ModelAndView("/book/create");
//        modelAndView.addObject("book",new Book());
//        return modelAndView;
//    }
//
//    @PostMapping("/create-book")
//    public ModelAndView saveBook(@ModelAttribute("book") BookForm bookForm) {
//
//        //Lay File anh
//        MultipartFile file = bookForm.getImage();
//
//        //Lay ten File
//        String fileName = file.getOriginalFilename();
//
//        //Lay Thong tin Book
//        String name = bookForm.getName();
//        int price = bookForm.getPrice();
//        String author = bookForm.getAuthor();
//        Category category = bookForm.getCategory();
//
//        //Copy File
//        try {
//            FileCopyUtils.copy(file.getBytes(),new File(fileUpload + fileName));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Book book = new Book(name,price,author,fileName,category);
//
//        bookService.save(book);
//        ModelAndView modelAndView = new ModelAndView("/book/create");
//        modelAndView.addObject("book", new Book());
//        modelAndView.addObject("message", "New book created successfully");
//        return modelAndView;
//    }
//
//    @GetMapping("/books")
//    public ModelAndView listBook( Pageable pageable, @RequestParam("search") Optional<String> search) {
//        Page<Book> books ;
//        if (search.isPresent()){
//            books = bookService.findAllByNameContaining(search.get(),pageable);
//        }
//        else {
//            books = bookService.findAll(pageable);
//        }
//        ModelAndView modelAndView = new ModelAndView("/book/list");
//        modelAndView.addObject("books", books);
//        return modelAndView;
//    }
//
//    @GetMapping("/edit-book/{id}")
//    public ModelAndView showEditForm(@PathVariable Long id) {
//        Optional<Book> book = bookService.findById(id);
//        if (book.isPresent()) {
//            ModelAndView modelAndView = new ModelAndView("/book/edit");
//            modelAndView.addObject("book", book.get());
//            return modelAndView;
//        } else {
//            ModelAndView modelAndView = new ModelAndView("/error.404");
//            return modelAndView;
//        }
//    }
//
//    @PostMapping("/edit-book")
//    public ModelAndView updateCustomer(@ModelAttribute("book") Book book) {
//        bookService.save(book);
//        ModelAndView modelAndView = new ModelAndView("/book/edit");
//        modelAndView.addObject("book", book);
//        modelAndView.addObject("message", "Book updated successfully");
//        return modelAndView;
//    }
//
//    @GetMapping("/delete-book/{id}")
//    public ModelAndView showDeleteForm(@PathVariable Long id) {
//        Optional<Book> book = bookService.findById(id);
//        if (book.isPresent()) {
//            ModelAndView modelAndView = new ModelAndView("/book/delete");
//            modelAndView.addObject("book", book.get());
//            return modelAndView;
//
//        } else {
//            ModelAndView modelAndView = new ModelAndView("/error.404");
//            return modelAndView;
//        }
//    }
//
//    @PostMapping("/delete-book")
//    public String deleteCustomer(@ModelAttribute("book") Book book) {
//        bookService.remove(book.getId());
//        return "redirect:books";
//    }

    @GetMapping("/noname")
    public ModelAndView getAllBook() {
        ModelAndView modelAndView = new ModelAndView("/noname");
        modelAndView.addObject("books", bookService.findAll());
        return modelAndView;
    }

    @GetMapping("")
    public ResponseEntity<Iterable<Book>> findAllBook(){
        List<Book> books = (List<Book>) bookService.findAll();
        if (books.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(books,HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Book> findBookById(@PathVariable Long id){
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(bookOptional.get(),HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<Book> saveBook(@RequestBody Book book){
        bookService.save(book);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(@PathVariable Long id, @RequestBody Book book) {
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        book.setId(bookOptional.get().getId());
        bookService.save(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteBook(@PathVariable Long id) {
        Optional<Book> bookOptional = bookService.findById(id);
        if (!bookOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        bookService.remove(id);
        return new ResponseEntity<>(bookOptional.get(), HttpStatus.NO_CONTENT);
    }
}

