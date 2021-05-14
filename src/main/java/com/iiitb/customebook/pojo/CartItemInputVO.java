package com.iiitb.customebook.pojo;

public class CartItemInputVO {

    private Integer bookId;
    private Integer chapterNumber;

    public CartItemInputVO() {
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
}
