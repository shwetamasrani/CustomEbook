package com.iiitb.customebook.bean;

import javax.persistence.*;

@Entity
@Table(name="Books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;
    @Column(nullable = false)
    private String bookName;
    @Column(nullable = false)
    private String isbnNumber;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String publisher;
    @Column
    private Integer yearOfRelease;
    @Column(nullable = false)
    private Double price;
    @Column
    private String imageLocation;
    @Column
    private String pdfFileLocation;
    @Column
    private String description;
    @Column
    private String xmlFileLocation;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user_id;

    public Book() {
    }

    public Book(String bookName, String isbnNumber, String author, String publisher, Integer yearOfRelease,
                Double price, String imageLocation, String pdfFileLocation, String description, String xmlFileLocation, User user_id) {
        this.bookName = bookName;
        this.isbnNumber = isbnNumber;
        this.author = author;
        this.publisher = publisher;
        this.yearOfRelease = yearOfRelease;
        this.price = price;
        this.imageLocation = imageLocation;
        this.pdfFileLocation = pdfFileLocation;
        this.description = description;
        this.xmlFileLocation = xmlFileLocation;
        this.user_id = user_id;
    }

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

    public String getPdfFileLocation() {
        return pdfFileLocation;
    }

    public void setPdfFileLocation(String pdfFileLocation) {
        this.pdfFileLocation = pdfFileLocation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getXmlFileLocation() {
        return xmlFileLocation;
    }

    public void setXmlFileLocation(String xmlFileLocation) {
        this.xmlFileLocation = xmlFileLocation;
    }

    public User getUser_id() {
        return user_id;
    }

    public void setUser_id(User user_id) {
        this.user_id = user_id;
    }
}
