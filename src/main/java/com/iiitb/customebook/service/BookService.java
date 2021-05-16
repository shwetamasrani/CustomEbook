package com.iiitb.customebook.service;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.bean.User;
import com.iiitb.customebook.exception.ResourceNotFoundException;
import com.iiitb.customebook.pojo.BookVO;
import com.iiitb.customebook.repository.BookRepository;
import com.iiitb.customebook.util.CustomEBookConstants;
import com.iiitb.customebook.util.CustomEBookUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private final BookRepository bookRepository;

    @Autowired
    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void savePdfFile(MultipartFile pdfFile, Integer bookId) {
         System.out.println("in book service");
        System.out.println(bookId);
        System.out.println(pdfFile.getSize());
        Book book= bookRepository.findById(bookId).orElseThrow(()
                -> new ResourceNotFoundException("Book does not exist with id:"+ bookId));
        System.out.println(book.getBookId());
        String pdfFolderPath = CustomEBookConstants.PATH_BOOKS+File.separator+bookId+".pdf";
        System.out.println(pdfFolderPath);
        book.setPdfFileLocation(pdfFolderPath);
        bookRepository.save(book);
        try {
            Files.copy(pdfFile.getInputStream(), Paths.get(pdfFolderPath), StandardCopyOption.REPLACE_EXISTING);

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public Book getBookById(Integer id){

        Book book= bookRepository.findById(id).orElseThrow(()
                -> new ResourceNotFoundException("Book does not exist with id:"+id));

        return book;
    }

    public List<BookVO> getAllBooks()
    {
        List<Book> bookList=bookRepository.findAll();
        List<BookVO> bookVoList=new ArrayList<>();

        for(int i=0;i<bookList.size();i++) {

             bookVoList.add(CustomEBookUtil.mappingBookBeanToPojo(bookList.get(i)));
        }
        return bookVoList;
    }

    public BookVO addBook(BookVO bookDetails)
    {
        if(bookDetails.getIsbnNumber()!=null && getBookByIsbnNumber(bookDetails.getIsbnNumber())==null) {
            Book book =  bookRepository.save(CustomEBookUtil.mappingBookVOToBean(bookDetails));
            return CustomEBookUtil.mappingBookBeanToPojo(book);
        }
        return null;
    }

    public BookVO uploadBook(BookVO bookDetails, User publisher)
    {
        if(bookDetails.getIsbnNumber()!=null && getBookByIsbnNumber(bookDetails.getIsbnNumber())==null) {
            Book newBook = CustomEBookUtil.mappingBookVOToBean(bookDetails);
            newBook.setUser_id(publisher);
            return CustomEBookUtil.mappingBookBeanToPojo(bookRepository.save(newBook));
        }
        return null;
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

    public BookVO getBookByIsbnNumber(String isbnNumber){
        Book book =  bookRepository.findByIsbnNumber(isbnNumber);
        if(book!=null) {
            return CustomEBookUtil.mappingBookBeanToPojo(book);
        }
        return null;
    }

    public BookVO splitBookIntoChapters(BookVO book) {

        try {
            String xmlFilePath = CustomEBookConstants.PATH_BOOKS_XML+File.separator+book.getBookId()+CustomEBookConstants.XML_FILE_EXTENSION;
            CustomEBookUtil.createXMlFile(book, xmlFilePath);
            Book bookInDB= bookRepository.findById(book.getBookId()).orElseThrow(()
                    -> new ResourceNotFoundException("Book does not exist with id:"+book.getBookId()));
            bookInDB.setXmlFileLocation(xmlFilePath);
            bookInDB = bookRepository.save(bookInDB);
            return CustomEBookUtil.mappingBookBeanToPojo(bookInDB);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
