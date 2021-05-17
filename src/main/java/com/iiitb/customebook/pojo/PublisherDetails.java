package com.iiitb.customebook.pojo;

import java.util.List;

public class PublisherDetails extends UserDetails{
    private String companyName;
    private List<BookVO> uploadedBooks;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<BookVO> getUploadedBooks() {
        return uploadedBooks;
    }

    public void setUploadedBooks(List<BookVO> uploadedBooks) {
        this.uploadedBooks = uploadedBooks;
    }

    public PublisherDetails(UserDetails userDetails) {
        super(userDetails.userId, userDetails.publisherFlag, userDetails.emailAddress);
    }
}
