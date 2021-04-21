package com.iiitb.customebook.service;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.exception.ResourceNotFoundException;
import com.iiitb.customebook.pojo.BookVO;
import com.iiitb.customebook.repository.BookRepository;
import com.iiitb.customebook.util.CustomEBookConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class BookService extends BookXMLService{

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public BookVO getBookById(Integer id){

        Book book= bookRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Book does not exist with id:"+id));

        return mappingBeanToPojo(book);
    }

    public Book addBook(BookVO bookDetails)
    {
        return bookRepository.save(mappingPojoToBean(bookDetails));

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

    public BookVO mappingBeanToPojo(Book book) {

        BookVO bookPOJO = new BookVO();
        bookPOJO.setBookId(book.getBookId());
        bookPOJO.setBookName(book.getBookName());
        bookPOJO.setAuthor(book.getAuthor());
        bookPOJO.setPrice(book.getPrice());
        bookPOJO.setIsbnNumber(book.getIsbnNumber());
        bookPOJO.setYearOfRelease(book.getYearOfRelease());
        bookPOJO.setImageLocation(book.getImageLocation());
        //bookPOJO.setBookchapters(readBookXMLFile(book.getXmlFileLocation()));
        return bookPOJO;
    }

    public Book mappingPojoToBean(BookVO bookDetails) {
        Book book = new Book();
        book.setBookName(bookDetails.getBookName());
        book.setIsbnNumber(bookDetails.getIsbnNumber());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublisher(bookDetails.getPublisher());
        book.setYearOfRelease(bookDetails.getYearOfRelease());
        book.setPrice(bookDetails.getPrice());
        book.setImageLocation(bookDetails.getImageLocation());
        book.setPdfFileLocation(bookDetails.getPdfFileLocation());
        book.setXmlFileLocation(CustomEBookConstants.NULL_STRING);
        return book;
    }

    public void splitBookIntoChapters(BookVO book) {

        try {
            String xmlFilePath = CustomEBookConstants.PATH_BOOKS_XML+File.separator+book.getBookId()+CustomEBookConstants.XML_FILE_EXTENSION;
            createXMlFile(book, xmlFilePath);
            Book bookInDB= bookRepository.findById(book.getBookId()).orElseThrow(()
                    -> new ResourceNotFoundException("Book does not exist with id:"+book.getBookId()));
            bookInDB.setXmlFileLocation(xmlFilePath);
            bookRepository.save(bookInDB);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
