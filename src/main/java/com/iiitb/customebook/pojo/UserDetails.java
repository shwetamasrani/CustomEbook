package com.iiitb.customebook.pojo;

import java.util.List;

public class UserDetails {

    protected int userId;
    protected boolean publisherFlag;
    protected String emailAddress;


    public UserDetails() {
    }

    public UserDetails(int userId, boolean publisherFlag, String emailAddress) {
        this.userId = userId;
        this.publisherFlag = publisherFlag;
        this.emailAddress = emailAddress;
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




}
