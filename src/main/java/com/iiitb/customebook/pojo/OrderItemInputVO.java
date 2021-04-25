package com.iiitb.customebook.pojo;

public class OrderItemInputVO {

    private Integer bookId;
    private Integer chapterNumber;
    private double price;
    private Integer startPage;
    private Integer endPage;

    public OrderItemInputVO() {
    }

    public OrderItemInputVO(Integer bookId, Integer chapterNumber, double price, Integer startPage, Integer endPage) {
        this.bookId = bookId;
        this.chapterNumber = chapterNumber;
        this.price = price;
        this.startPage = startPage;
        this.endPage = endPage;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public Integer getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(Integer chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getStartPage() {
        return startPage;
    }

    public void setStartPage(Integer startPage) {
        this.startPage = startPage;
    }

    public Integer getEndPage() {
        return endPage;
    }

    public void setEndPage(Integer endPage) {
        this.endPage = endPage;
    }
}
