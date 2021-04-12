package com.iiitb.customebook.pojo;

import javax.xml.bind.annotation.*;
@XmlAccessorType (XmlAccessType.FIELD)
public class BookChapterVO {

    private Integer chapterNumber;
    private String chapterName;
    private Double price;
    private String contentLocation;

    public Integer getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(Integer chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getContentLocation() {
        return contentLocation;
    }

    public void setContentLocation(String contentLocation) {
        this.contentLocation = contentLocation;
    }

    public BookChapterVO() {
    }

    public BookChapterVO(Integer chapterNumber, String chapterName, Double price, String contentLocation) {
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.price = price;
        this.contentLocation = contentLocation;
    }
}
