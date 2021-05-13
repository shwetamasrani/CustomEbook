package com.iiitb.customebook.pojo;

import java.util.List;

public class UserDetails {

    private int userId;
    private boolean publisherFlag;
    private String emailAddress;
    private String companyName;
    private List<BookVO> uploadedBooks;
    private String firstName;
    private String lastName;
    private List<OrderDetails> orders;

    public UserDetails() {
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setPublisherFlag(boolean publisherFlag) {
        this.publisherFlag = publisherFlag;
    }

    public int getUserId() {
        return userId;
    }

    public boolean isPublisherFlag() {
        return publisherFlag;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

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

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<OrderDetails> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDetails> orders) {
        this.orders = orders;
    }
}
