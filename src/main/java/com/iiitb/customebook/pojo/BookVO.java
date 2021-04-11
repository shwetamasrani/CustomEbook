package com.iiitb.customebook.pojo;

import java.util.List;

public class BookVO {

    private Integer book_id;

    private String book_name;

    private String isbnNumber;

    private String author;

    private String publisher;

    private Integer year_of_release;

    private Double price;

    private String imageLocation;

    private List<BookChapterVO> bookchapters;

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

    public List<BookChapterVO> getBookchapters() {
        return bookchapters;
    }

    public void setBookchapters(List<BookChapterVO> bookchapters) {
        this.bookchapters = bookchapters;
    }

    public BookVO() {
    }

    public BookVO(Integer book_id, String book_name, String isbnNumber, String author, String publisher, Integer year_of_release, Double price, String imageLocation, List<BookChapterVO> bookchapters) {
        this.book_id = book_id;
        this.book_name = book_name;
        this.isbnNumber = isbnNumber;
        this.author = author;
        this.publisher = publisher;
        this.year_of_release = year_of_release;
        this.price = price;
        this.imageLocation = imageLocation;
        this.bookchapters = bookchapters;
    }
}
