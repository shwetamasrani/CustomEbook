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

    @Column
    private String companyName;

    @OneToMany(mappedBy = "user_id")
    private List<Order> orders;

    @OneToMany(mappedBy = "user_id")
    private List<Book> uploaded_books;

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

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public List<Book> getUploaded_books() {
        return uploaded_books;
    }

    public void setUploaded_books(List<Book> uploaded_books) {
        this.uploaded_books = uploaded_books;
    }

    public boolean isPublisherFlag() {
        return publisherFlag;
    }

    public void setPublisherFlag(boolean publisherFlag) {
        this.publisherFlag = publisherFlag;
    }

    public User(String firstName, String lastName, String email, String contactNumber, String password, String companyName, List<Order> orders, List<Book> uploaded_books, boolean publisherFlag) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.password = password;
        this.companyName = companyName;
        this.orders = orders;
        this.uploaded_books = uploaded_books;
        this.publisherFlag = publisherFlag;
    }

    public User() {
    }
}
