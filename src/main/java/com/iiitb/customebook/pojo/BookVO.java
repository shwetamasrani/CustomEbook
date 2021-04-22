package com.iiitb.customebook.pojo;

import org.springframework.web.multipart.MultipartFile;

import javax.xml.bind.annotation.*;
import java.util.List;

@XmlRootElement(name = "book")
@XmlAccessorType (XmlAccessType.FIELD)
public class BookVO {

    private Integer bookId;

    private String bookName;

    private String isbnNumber;

    private String author;

    private String publisher;

    private Integer yearOfRelease;

    private Double price;

    private String imageLocation;

    private String description;

    private String pdfFileLocation;



    private Integer noOfChapters;


    @XmlElementWrapper(name = "chapters")
    @XmlElement(name = "chapter")
    private List<BookComponent> bookChapters;

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getIsbnNumber() {
        return isbnNumber;
    }

    public void setIsbnNumber(String isbnNumber) {
        this.isbnNumber = isbnNumber;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public Integer getYearOfRelease() {
        return yearOfRelease;
    }

    public void setYearOfRelease(Integer yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPdfFileLocation() {
        return pdfFileLocation;
    }

    public void setPdfFileLocation(String pdfFileLocation) {
        this.pdfFileLocation = pdfFileLocation;
    }

    public List<BookComponent> getBookChapters() {
        return bookChapters;
    }

    public void setBookchapters(List<BookComponent> bookChapters) {
        this.bookChapters = bookChapters;
    }

    public Integer getNoOfChapters() {
        return noOfChapters;
    }

    public void setNoOfChapters(Integer noOfChapters) {
        this.noOfChapters = noOfChapters;
    }

    public BookVO() {
    }

    public BookVO(String bookName, String isbnNumber, String author, String publisher, Integer yearOfRelease, Double price, String imageLocation, String description, String pdfFileLocation, List<BookComponent> bookChapters,Integer noOfChapters) {
        this.bookName = bookName;
        this.isbnNumber = isbnNumber;
        this.author = author;
        this.publisher = publisher;
        this.yearOfRelease = yearOfRelease;
        this.price = price;
        this.imageLocation = imageLocation;
        this.description = description;
        this.pdfFileLocation = pdfFileLocation;
        this.bookChapters = bookChapters;
        this.noOfChapters=noOfChapters;
    }
}
