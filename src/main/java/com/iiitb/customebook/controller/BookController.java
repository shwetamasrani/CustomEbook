package com.iiitb.customebook.controller;


import com.iiitb.customebook.pojo.BookVO;

import com.iiitb.customebook.service.BookService;
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
        BookVO book = bookService.getBookById(id);
        return ResponseEntity.ok(book);  //entity is returned along with the status
    }

    @GetMapping
    public List<BookVO> getAllBooks(){
        return bookService.getAllBooks();
    }

    @GetMapping("isbn/{isbn}")
    public ResponseEntity<BookVO> getBookById(@PathVariable String isbn) {
        BookVO book = bookService.getBookByIsbnNumber(isbn);
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
}
