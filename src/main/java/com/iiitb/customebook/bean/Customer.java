package com.iiitb.customebook.bean;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;              //Primary Key
    @Column(nullable = false)
    private String customerName;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String emailId;
    @Column(nullable = false)
    private String contactNumber;
    @Column(nullable = false, unique = true)
    private Long tradingAccount;

    public Customer(Integer customerId,
                    String customerName,
                    String password,
                    String emailId,
                    String contactNumber,
                    Long tradingAccount) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.password = password;
        this.emailId = emailId;
        this.contactNumber = contactNumber;
        this.tradingAccount = tradingAccount;
    }

    public Customer() {

    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public Long getTradingAccount() {
        return tradingAccount;
    }

    public void setTradingAccount(Long tradingAccount) {
        this.tradingAccount = tradingAccount;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "customerId=" + customerId +
                ", customerName='" + customerName + '\'' +
                ", password='" + password + '\'' +
                ", emailId='" + emailId + '\'' +
                ", contactNumber='" + contactNumber + '\'' +
                ", tradingAccount=" + tradingAccount +
                '}';
    }
}

    
