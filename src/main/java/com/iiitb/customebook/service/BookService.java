package com.iiitb.customebook.service;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.exception.ResourceNotFoundException;
import com.iiitb.customebook.pojo.BookVO;
import com.iiitb.customebook.repository.BookRepository;
import com.iiitb.customebook.util.CustomEBookConstants;
import com.iiitb.customebook.util.CustomEBookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookVO getBookById(Integer id){

        Book book= bookRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Book does not exist with id:"+id));

        return CustomEBookUtil.mappingBeanToPojo(book);
    }

    public Book addBook(BookVO bookDetails)
    {
        return bookRepository.save(CustomEBookUtil.mappingPojoToBean(bookDetails));

    }

    public List<Book> getBooksByPublisher(String publisher){
       return bookRepository.findByPublisher(publisher);

    }

    public List<Book> getBooksByAuthor(String author){
        return bookRepository.findByAuthor(author);

    }

   public  List<Book> getBookByBookName(String bookName){
        return bookRepository.findByBookName(bookName);

    }

    public Book getBookByIsbnNumber(String isbnNumber){
        return bookRepository.findByIsbnNumber(isbnNumber);

    }

    public void splitBookIntoChapters(BookVO book) {

        try {
            String xmlFilePath = CustomEBookConstants.PATH_BOOKS_XML+File.separator+book.getBookId()+CustomEBookConstants.XML_FILE_EXTENSION;
            CustomEBookUtil.createXMlFile(book, xmlFilePath);
            Book bookInDB= bookRepository.findById(book.getBookId()).orElseThrow(()
                    -> new ResourceNotFoundException("Book does not exist with id:"+book.getBookId()));
            bookInDB.setXmlFileLocation(xmlFilePath);
            bookRepository.save(bookInDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
