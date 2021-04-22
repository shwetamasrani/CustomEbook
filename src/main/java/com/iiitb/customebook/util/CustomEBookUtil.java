package com.iiitb.customebook.util;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.pojo.BookVO;

public class CustomEBookUtil {

    public static BookVO mappingBeanToPojo(Book book) {

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

    public static Book mappingPojoToBean(BookVO bookDetails) {
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
}
