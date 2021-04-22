package com.iiitb.customebook.pojo;

public class InvoiceItemInputVO {

    private Integer bookId;
    private Integer chapterNumber;

    public InvoiceItemInputVO() {
    }

    public InvoiceItemInputVO(Integer bookId, Integer chapterNumber) {
        this.bookId = bookId;
        this.chapterNumber = chapterNumber;
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
