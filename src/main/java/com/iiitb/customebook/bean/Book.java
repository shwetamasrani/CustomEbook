package com.iiitb.customebook.bean;

import javax.persistence.*;

public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer book_id;
    @Column(nullable = false)
    private String book_name;
    @Column(nullable = false)
    private String isbnNumber;
    @Column(nullable = false)
    private String author;
    @Column(nullable = false)
    private String publisher;
    @Column
    private Integer year_of_release;
    @Column(nullable = false)
    private Double price;
    @Column(nullable = false)
    private String imageLocation;
    @Column(nullable = false)
    private String fileLocation;

    public Integer getBook_id() {
        return book_id;
    }

    public void setBook_id(Integer book_id) {
        this.book_id = book_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
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

    public Integer getYear_of_release() {
        return year_of_release;
    }

    public void setYear_of_release(Integer year_of_release) {
        this.year_of_release = year_of_release;
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

    public String getFileLocation() {
        return fileLocation;
    }

    public void setFileLocation(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    public Book() {
    }

    public Book(String book_name, String isbnNumber, String author, String publisher, Integer year_of_release, Double price, String imageLocation, String fileLocation) {
        this.book_name = book_name;
        this.isbnNumber = isbnNumber;
        this.author = author;
        this.publisher = publisher;
        this.year_of_release = year_of_release;
        this.price = price;
        this.imageLocation = imageLocation;
        this.fileLocation = fileLocation;
    }
}
