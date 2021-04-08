package com.iiitb.customebook.bean;
import javax.persistence.*;

@Entity
@Table
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int userId;

    @Column
    private String firstName;

    @Column
    private String lastName;

    @Column(length=50,nullable = false,unique = true)
    private String email;

    @Column(length=10)
    private Integer contactNumber;

    @Column(length=50,nullable = false)
    private String password;

    @Column
    private Boolean isPublisherFlag; //0-> user   1->Publisher

    @Column()
    private String companyName;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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

    public Integer getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(Integer contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getPublisherFlag() {
        return isPublisherFlag;
    }

    public void setPublisherFlag(Boolean publisherFlag) {
        isPublisherFlag = publisherFlag;
    }

    public User() {
    }

    public User(String firstName, String lastName, String email, Integer contactNumber, String password, Boolean isPublisherFlag, String companyName) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.contactNumber = contactNumber;
        this.password = password;
        this.isPublisherFlag = isPublisherFlag;
        this.companyName=companyName;

    }
}