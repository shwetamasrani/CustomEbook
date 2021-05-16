package com.iiitb.customebook.controller;


import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.pojo.BookVO;

import com.iiitb.customebook.service.BookService;
import com.iiitb.customebook.util.CustomEBookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<BookVO> getBookById(@PathVariable String isbn) {
        BookVO book = bookService.getBookByIsbnNumber(isbn);
        if(null!=book) {
            return ResponseEntity.ok(book);
        }
        return null;
    }

    @PostMapping
    public ResponseEntity<Object> addBook(@RequestBody BookVO bookDetails)  //mapping the JSON Body to the object directly
    {
        if(bookDetails!=null) {
            BookVO book = bookService.addBook(bookDetails);
                if(book!=null) {
                    return new ResponseEntity(book, HttpStatus.CREATED);
                }
                else {
                    return ResponseEntity.badRequest().body("Book already exists");
                }
        }
        return null;

    }

    @PostMapping(value="/savePdfFile" ,produces= { "application/json" },
            consumes = { "*/*" })
    public ResponseEntity<String> savePdfFile(@RequestParam("file") MultipartFile file, @RequestParam("bookId") Integer bookId)
    {
        System.out.println(file.getOriginalFilename());
        System.out.println(bookId);

        if(file!=null) {
            bookService.savePdfFile(file,bookId);

        }
 return ResponseEntity.ok("working");
    }


}
