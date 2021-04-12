package com.iiitb.customebook.service;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.bean.User;
import com.iiitb.customebook.exception.ResourceNotFoundException;
import com.iiitb.customebook.repository.BookRepository;
import com.iiitb.customebook.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class BookService {

    private final BookRepository bookRepository;


    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

   /* public ResponseEntity<Book> getBookById(Integer id){

        Book book= BookRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Book does not exists with id:"+id));

        return ResponseEntity.ok(book);  //entity is returned along with the status
    }*/
}
