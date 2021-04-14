package com.iiitb.customebook.service;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.bean.User;
import com.iiitb.customebook.exception.ResourceNotFoundException;
import com.iiitb.customebook.repository.BookRepository;
import com.iiitb.customebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;


    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }


    public Book  createBook(Book book)
    {
        System.out.println(book.toString());
        return bookRepository.save(book);
    }

    public List<Book> getBooksByPublisher(String publisher){
       return bookRepository.findByPublisher(publisher);

    }

    public List<Book> getBooksByAuthor(String author){
        return bookRepository.findByAuthor(author);

    }

    public Book getBookByBookName(String book_name){
        return bookRepository.findByBookName(book_name);

    }

    public List<Book> getBookByIsbnNumber(String isbnNumber){
        return bookRepository.findByIsbnNumber(isbnNumber);

    }
   /*public ResponseEntity<Book> getBookById(Integer id){

        Book book= BookRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Book does not exists with id:"+id));

        return ResponseEntity.ok(book);  //entity is returned along with the status
    }*/
}
