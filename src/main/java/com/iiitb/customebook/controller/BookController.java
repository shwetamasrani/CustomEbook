package com.iiitb.customebook.controller;


import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.pojo.BookVO;

import com.iiitb.customebook.service.BookService;
import com.iiitb.customebook.util.CustomEBookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/books")
public class BookController {

    private BookService bookService;

    @Autowired
    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookVO> getBookById(@PathVariable Integer id) {
        Book book = bookService.getBookById(id);
        return ResponseEntity.ok(CustomEBookUtil.mappingBookBeanToPojo(book));  //entity is returned along with the status
    }

    @PutMapping("/split")
    public ResponseEntity<BookVO> splitBook(@RequestBody BookVO bookDetails)
    {
        if(null!=bookDetails ) {
            if(bookDetails.getBookId()!=null) {
                if(bookDetails.getBookChapters()!=null) {
                    bookDetails = bookService.splitBookIntoChapters(bookDetails);
                    return ResponseEntity.ok(bookDetails);
                }
            }
        }
        return null;
    }

    @GetMapping
    public List<BookVO> getAllBooks(){
        return bookService.getAllBooks();
    }


    @GetMapping("isbn/{isbn}")
    public ResponseEntity<Book> getBookById(@PathVariable String isbn) {
        Book book = bookService.getBookByIsbnNumber(isbn);
        if(null!=book) {
            return ResponseEntity.ok(book);
        }
        return null;
    }
    @GetMapping("bookName/{bookName}")
    public ResponseEntity<List<Book>> getBooksByBookName(@PathVariable String bookName) {
        List<Book> book = bookService.getBooksByBookName(bookName);
        if(null!=book) {
            return ResponseEntity.ok(book);
        }
        return null;
    }
    @GetMapping("author/{author}")
    public ResponseEntity<List<Book>> getBooksByAuthor(@PathVariable String author) {
        List<Book> book = bookService.getBooksByAuthor(author);
        if(null!=book) {
            return ResponseEntity.ok(book);
        }
        return null;
    }
    @GetMapping("publisher/{publisher}")
    public ResponseEntity<List<Book>> getBooksByPublisher(@PathVariable String publisher) {
        List<Book> book = bookService.getBooksByPublisher(publisher);
        if(null!=book) {
            return ResponseEntity.ok(book);
        }
        return null;
    }
    @PostMapping
    public ResponseEntity<BookVO> addBook(@RequestBody BookVO bookDetails)  //mapping the JSON Body to the object directly
    {
        if(bookDetails!=null) {
            if(bookDetails.getIsbnNumber()!=null && bookService.getBookByIsbnNumber(bookDetails.getIsbnNumber())==null) {
                bookDetails = bookService.addBook(bookDetails);
                return new ResponseEntity(bookDetails, HttpStatus.CREATED);
            }
        }
        return null;
    }


}
