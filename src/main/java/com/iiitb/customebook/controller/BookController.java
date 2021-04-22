package com.iiitb.customebook.controller;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.pojo.BookComponent;
import com.iiitb.customebook.pojo.BookVO;
import com.iiitb.customebook.repository.BookRepository;
import com.iiitb.customebook.repository.UserRepository;
import com.iiitb.customebook.service.BookService;
import com.iiitb.customebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping("isbn/{isbn}")
    public ResponseEntity<BookVO> getBookById(@PathVariable String isbn) {
        BookVO book = bookService.getBookByIsbnNumber(isbn);
        if(null!=book) {
            return ResponseEntity.ok(book);
        }
        return null;
    }

    @PostMapping
    public void addBook(@RequestBody BookVO bookDetails)  //mapping the JSON Body to the object directly
    {
        if(bookDetails!=null) {
            if(bookDetails.getIsbnNumber()!=null && bookService.getBookByIsbnNumber(bookDetails.getIsbnNumber())==null) {
                bookService.addBook(bookDetails);
            } else {
                System.out.println("Invalid ISBN Number");
            }
        }


    }
    @PostMapping("/split")
    public void splitBook(@RequestBody BookVO book)
    {
        if(null!=book ) {
            if(book.getBookId()!=null) {
                System.out.println("bookid is=="+book.getBookId());
                if(book.getBookChapters()!=null) {
                    System.out.println("chapters size is=="+book.getBookChapters().size());
                    bookService.splitBookIntoChapters(book);
                } else{
                    System.out.println("bookchapters is null");
                }
            } else {
                System.out.println("bookid is null");
            }
        }else {
            System.out.println("obj is null");
        }
    }
}
