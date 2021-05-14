package com.iiitb.customebook.pojo;

import java.util.List;

public class CustomerDetails extends UserDetails{

    private String firstName;
    private String lastName;
    private List<OrderDetails> orders;
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

    public CustomerDetails(UserDetails userDetails) {
        super(userDetails.userId, userDetails.publisherFlag, userDetails.emailAddress);
    }
}
