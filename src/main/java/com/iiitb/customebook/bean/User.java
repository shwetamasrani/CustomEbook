package com.iiitb.customebook.bean;

import javax.persistence.*;
import java.util.*;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int user_id;

    @Column(nullable = true)
    private String firstName;

    @Column(nullable = true)
    private String lastName;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 10)
    private String contactNumber;

    @Column(length = 50, nullable = false)
    private String password;

    /*@Column( nullable = true,columnDefinition = "boolean default false")
    private boolean isPublisherFlag; //0-> user   1->Publisher*/

    @Column(nullable = false)
    private String companyName;

    @OneToMany(mappedBy = "user_id")
    private List<Invoice> invoices;

    @OneToMany(mappedBy = "user_id")
    private List<PublisherUploads> uploaded_books;

    @Column( nullable = true,columnDefinition = "boolean default false")
    private boolean publisherFlag;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Invoice> getInvoices() {
        return invoices;
    }

    public void setInvoices(List<Invoice> invoices) {
        this.invoices = invoices;
    }

    public boolean isPublisherFlag() {
        return publisherFlag;
    }

    public void setPublisherFlag(boolean publisherFlag) {
        this.publisherFlag = publisherFlag;
    }

    public User(int user_id, String firstName, String lastName, String email, String contactNumber, String password, String companyName, List<Invoice> invoices, List<PublisherUploads> uploaded_books, boolean publisherFlag) {
        this.user_id = user_id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.password = password;
        this.companyName = companyName;
        this.invoices = invoices;
        this.uploaded_books = uploaded_books;
        this.publisherFlag = publisherFlag;
    }

    public User() {
    }
}