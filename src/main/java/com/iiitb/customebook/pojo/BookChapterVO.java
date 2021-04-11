package com.iiitb.customebook.pojo;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class BookChapterVO {

    public String chapterName;
    public Integer chapterNumber;
    public Double chapterPrice;
    public String chapterContent;

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Integer getChapterNumber() {
        return chapterNumber;
    }

    public void setChapterNumber(Integer chapterNumber) {
        this.chapterNumber = chapterNumber;
    }

    public Double getChapterPrice() {
        return chapterPrice;
    }

    public void setChapterPrice(Double chapterPrice) {
        this.chapterPrice = chapterPrice;
    }

    public String getChapterContent() {
        return chapterContent;
    }

    public void setChapterContent(String chapterContent) {
        this.chapterContent = chapterContent;
    }

    public BookChapterVO() {
    }

    public BookChapterVO(String chapterName, Integer chapterNumber, Double chapterPrice, String chapterContent) {
        this.chapterName = chapterName;
        this.chapterNumber = chapterNumber;
        this.chapterPrice = chapterPrice;
        this.chapterContent = chapterContent;
    }
}
