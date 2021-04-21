package com.iiitb.customebook.pojo;

import javax.xml.bind.annotation.*;
@XmlAccessorType (XmlAccessType.FIELD)
public class BookComponent {

    private Integer chapterNumber;
    private String chapterName;
    private Double price;
    private Integer startPage;
    private Integer endPage;
    private String description;

    public BookComponent() {
    }

    public BookComponent(Integer chapterNumber, String chapterName, Double price, Integer startPage, Integer endPage, String description) {
        this.chapterNumber = chapterNumber;
        this.chapterName = chapterName;
        this.price = price;
        this.startPage = startPage;
        this.endPage = endPage;
        this.description = description;
    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
