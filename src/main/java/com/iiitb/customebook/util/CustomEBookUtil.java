package com.iiitb.customebook.util;

import com.iiitb.customebook.bean.Book;
import com.iiitb.customebook.pojo.BookComponent;
import com.iiitb.customebook.pojo.BookVO;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

public class CustomEBookUtil {

    public static BookVO mappingBookBeanToPojo(Book book) {

        BookVO bookDetails = new BookVO();
        bookDetails.setBookId(book.getBookId());
        bookDetails.setBookName(book.getBookName());
        bookDetails.setAuthor(book.getAuthor());
        bookDetails.setPublisher(book.getPublisher());
        bookDetails.setPrice(book.getPrice());
        bookDetails.setIsbnNumber(book.getIsbnNumber());
        bookDetails.setYearOfRelease(book.getYearOfRelease());
        bookDetails.setImageLocation(book.getImageLocation());
        bookDetails.setDescription(book.getDescription());
        bookDetails.setPdfFileLocation(book.getPdfFileLocation());

        if(book.getXmlFileLocation()!=null) {
            bookDetails.setBookchapters(readXML(book.getXmlFileLocation()));
        }
        return bookDetails;
    }

    public static Book mappingBookVOToBean(BookVO bookDetails) {
        Book book = new Book();
        book.setBookName(bookDetails.getBookName());
        book.setIsbnNumber(bookDetails.getIsbnNumber());
        book.setAuthor(bookDetails.getAuthor());
        book.setPublisher(bookDetails.getPublisher());
        book.setYearOfRelease(bookDetails.getYearOfRelease());
        book.setPrice(bookDetails.getPrice());
        book.setImageLocation(bookDetails.getImageLocation());
        book.setPdfFileLocation(bookDetails.getPdfFileLocation());
        book.setDescription(bookDetails.getDescription());
        book.setXmlFileLocation(CustomEBookConstants.NULL_STRING);
        return book;
    }

    public static List<BookComponent> readXML(String xmlFileLocation) {

        try{
            File file = new File(xmlFileLocation);
            JAXBContext jaxbContext = JAXBContext.newInstance(BookVO.class);
            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            BookVO book= (BookVO) jaxbUnmarshaller.unmarshal(file);
            return book.getBookChapters();

        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void createXMlFile(BookVO book, String filePath) {
        try {
            JAXBContext contextObj = JAXBContext.newInstance(BookVO.class);
            Marshaller marshallerObj = contextObj.createMarshaller();
            marshallerObj.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshallerObj.marshal(book, new FileOutputStream(filePath));
        } catch (JAXBException | FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
